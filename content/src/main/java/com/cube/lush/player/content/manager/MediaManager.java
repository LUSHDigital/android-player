package com.cube.lush.player.content.manager;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.brightcove.player.edge.Catalog;
import com.brightcove.player.event.EventEmitterImpl;
import com.brightcove.player.model.Playlist;
import com.brightcove.player.model.Video;
import com.cube.lush.player.content.BuildConfig;
import com.cube.lush.player.api.LushAPI;
import com.cube.lush.player.api.model.VideoContent;
import com.cube.lush.player.api.model.MediaContent;
import com.cube.lush.player.api.model.Programme;
import com.cube.lush.player.api.model.RadioContent;
import com.cube.lush.player.content.dagger.DaggerComponents;
import com.cube.lush.player.content.handler.ResponseHandler;
import com.cube.lush.player.content.model.CategoryContentType;
import com.cube.lush.player.content.model.Channel;
import com.cube.lush.player.content.model.VideoInfo;
import com.google.gson.internal.bind.util.ISO8601Utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.inject.Inject;

import lombok.Getter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Manages requests for media, returning it to the requester from the most appropriate source. Currently gets data from API or in-memory cache.
 *
 * @author Jamie Cruwys
 * @project lush-player-android-client
 */
public class MediaManager
{
	private static final int SECOND = 1000;
	private static final int MINUTE = 60 * SECOND;
	private static final int HOUR = 60 * MINUTE;
	private static final int VIDEO_CACHE_EXPIRY_TIME = 5 * MINUTE;
	private static final int RADIO_CACHE_EXPIRY_TIME = 5 * MINUTE;
	private static final int LIVE_CACHE_EXPIRY_TIME = 0;

	private static MediaManager instance;

	public static MediaManager getInstance()
	{
		if (instance == null)
		{
			instance = new MediaManager();
		}

		return instance;
	}

	@Inject
	protected LushAPI api;

	@Getter
	private Catalog catalog;

	// Cached data
	private List<VideoContent> videoCache = null;
	private long lastVideoFetchTime = 0;
	private List<RadioContent> radioCache = null;
	private long lastRadioFetchTime = 0;
	private List<MediaContent> liveCache = null;
	private long lastLiveFetchTime = 0;

	private MediaManager()
	{
		DaggerComponents.getInstance().getApi().inject(this);
		catalog = new Catalog(new EventEmitterImpl(), BuildConfig.BRIGHTCOVE_ACCOUNT_ID, BuildConfig.BRIGHTCOVE_POLICY_KEY);
	}

	/**
	 * Determines which video, if any, in a playlist is currently "live"
	 *
	 * @param playlist
	 * @return
	 */
	@Nullable
	public VideoInfo findCurrentLiveVideo(Playlist playlist)
	{
		long nowUtc = System.currentTimeMillis();

		for (Video video: playlist.getVideos())
		{
			try
			{
				if (video.getProperties().get("customFields") instanceof Map)
				{
					Map customFields = (Map) video.getProperties().get("customFields");
					Object startTimeString = customFields.get("starttime");
					Object length = customFields.get("livebroadcastlength");
					if (startTimeString instanceof String && length instanceof String)
					{
						long startTimeUtc = ISO8601Utils.parse((String) startTimeString, new ParsePosition(0)).getTime();
						String[] lengthParts = ((String)length).split(":"); // length is in the format HH:MM:SS
						if (lengthParts.length != 3)
						{
							continue;
						}
						long endTimeUtc = startTimeUtc;
						endTimeUtc += Long.parseLong(lengthParts[2]) * SECOND;
						endTimeUtc += Long.parseLong(lengthParts[1]) * MINUTE;
						endTimeUtc += Long.parseLong(lengthParts[0]) * HOUR;

						if (nowUtc >= startTimeUtc && nowUtc < endTimeUtc)
						{
							VideoInfo videoInfo = new VideoInfo();
							videoInfo.setVideo(video);
							videoInfo.setStartTimeUtc(startTimeUtc);
							videoInfo.setEndTimeUtc(endTimeUtc);
							return videoInfo;
						}
					}
				}
			}
			catch (ParseException parseEx)
			{
				// Ignore parse exception
				Log.e("3SC", parseEx.getMessage(), parseEx);
			}
		}

		return null;
	}

	/**
	 * Gets all of the media content, which is videos and radios
	 *
	 * @param handler
	 */
	public void getMedia(@NonNull final ResponseHandler<MediaContent> handler)
	{
		final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
		final List<VideoContent> videoContent = new ArrayList<>();
		final List<RadioContent> radioContent = new ArrayList<>();

		// We only want to report failure if both TV and radio endpoints fail, otherwise just show what weve got
		final CountDownLatch failureCountdown = new CountDownLatch(2);

		getVideos(new ResponseHandler<VideoContent>()
		{
			@Override
			public void onSuccess(@NonNull List<VideoContent> items)
			{
				readWriteLock.writeLock().lock();
				try
				{
					videoContent.clear();
					videoContent.addAll(items);
					List<MediaContent> composite = new ArrayList<MediaContent>(videoContent);
					composite.addAll(radioContent);
					handler.onSuccess(composite);
				}
				finally
				{
					readWriteLock.writeLock().unlock();
				}
			}

			@Override
			public void onFailure(@Nullable Throwable t)
			{
				failureCountdown.countDown();
				if (failureCountdown.getCount() == 0)
				{
					handler.onFailure(t);
				}
			}
		});

		getRadios(new ResponseHandler<RadioContent>()
		{
			@Override
			public void onSuccess(@NonNull List<RadioContent> items)
			{
				readWriteLock.writeLock().lock();
				try
				{
					radioContent.clear();
					radioContent.addAll(items);
					List<MediaContent> composite = new ArrayList<MediaContent>(radioContent);
					composite.addAll(videoContent);
					handler.onSuccess(composite);
				}
				finally
				{
					readWriteLock.writeLock().unlock();
				}
			}

			@Override
			public void onFailure(@Nullable Throwable t)
			{
				failureCountdown.countDown();
				if (failureCountdown.getCount() == 0)
				{
					handler.onFailure(t);
				}
			}
		});
	}

	/**
	 * Gets all of the video content.
	 *
	 * @param handler
	 * 				Receives callback if retrieving the data succeeds or fails. If data is cached then it is possible for onSuccess to be called twice - once for the
	 * 				cached data, and (if the data is stale) once for the API response.
	 */
	public void getVideos(@NonNull final ResponseHandler<VideoContent> handler)
	{
		final boolean hasCachedData = videoCache != null;

		// Return cached data immediately if it is present
		if (hasCachedData)
		{
			handler.onSuccess(Collections.unmodifiableList(videoCache));

			// Only perform a network request if the cached data is stale
			if (System.currentTimeMillis() - lastVideoFetchTime < VIDEO_CACHE_EXPIRY_TIME)
			{
				return;
			}
		}

		final Call<List<VideoContent>> videoCall = api.getVideos();
		videoCall.enqueue(new Callback<List<VideoContent>>()
		{
			@Override
			public void onResponse(@NonNull final Call<List<VideoContent>> call, @NonNull final Response<List<VideoContent>> videoResponse)
			{
				if (!videoResponse.isSuccessful())
				{
					onFailure(call, null);
				}
				else
				{
					List<VideoContent> videos = videoResponse.body();

					if (videos == null)
					{
						videos = Collections.emptyList();
					}

					videoCache = videos;
					lastVideoFetchTime = System.currentTimeMillis();
					handler.onSuccess(Collections.unmodifiableList(videoCache));
				}
			}

			@Override
			public void onFailure(@Nullable final Call<List<VideoContent>> call, @Nullable final Throwable t)
			{
				// Only report failure if we didn't even report cache
				if (!hasCachedData)
				{
					handler.onFailure(t);
				}
			}
		});
	}

	/**
	 * Gets all of the radio content
	 *
	 * @param handler
	 * 				Receives callback if retrieving the data succeeds or fails. If data is cached then it is possible for onSuccess to be called twice - once for the
	 * 				cached data, and (if the data is stale) once for the API response.
	 */
	public void getRadios(@NonNull final ResponseHandler<RadioContent> handler)
	{
		final boolean hasCachedData = radioCache != null;

		// Return cached data immediately if it is present
		if (hasCachedData)
		{
			handler.onSuccess(Collections.unmodifiableList(radioCache));

			// Only perform a network request if the cached data is stale
			if (System.currentTimeMillis() - lastRadioFetchTime < RADIO_CACHE_EXPIRY_TIME)
			{
				return;
			}
		}

		final Call<List<RadioContent>> radioCall = api.getRadios();
		radioCall.enqueue(new Callback<List<RadioContent>>()
		{
			@Override
			public void onResponse(@NonNull final Call<List<RadioContent>> call, @NonNull final Response<List<RadioContent>> radioResponse)
			{
				if (!radioResponse.isSuccessful())
				{
					onFailure(call, null);
				}
				else
				{
					List<RadioContent> radios = radioResponse.body();

					if (radios == null)
					{
						radios = Collections.emptyList();
					}

					radioCache = radios;
					lastRadioFetchTime = System.currentTimeMillis();
					handler.onSuccess(Collections.unmodifiableList(radioCache));
				}
			}

			@Override
			public void onFailure(@Nullable final Call<List<RadioContent>> call, @Nullable final Throwable t)
			{
				// Only report failure if we didn't even report cache
				if (!hasCachedData)
				{
					handler.onFailure(t);
				}
			}
		});
	}

	/**
	 * Gets all of the channel content for a given channel object
	 *
	 * @param channel
	 * @param handler
	 */
	public void getChannelContent(@NonNull final Channel channel,
	                              @Nullable CategoryContentType contentType,
	                              @NonNull final ResponseHandler<MediaContent> handler)
	{
		getChannelContent(channel.getId(), contentType, handler);
	}

	/**
	 * Gets all of the channel content for a given channel id
	 *
	 * @param channelId
	 * @param handler
	 */
	public void getChannelContent(@NonNull final String channelId,
	                              @Nullable CategoryContentType contentType,
	                              @NonNull final ResponseHandler<MediaContent> handler)
	{
		String contentTypeName = contentType == null ? null : contentType.getApiContentType();
		Call<List<MediaContent>> channelCall = api.getCategories(channelId, contentTypeName);

		channelCall.enqueue(new Callback<List<MediaContent>>()
		{
			@Override
			public void onResponse(@NonNull final Call<List<MediaContent>> call, @NonNull final Response<List<MediaContent>> channelResponse)
			{
				if (!channelResponse.isSuccessful())
				{
					handler.onFailure(null);
				}
				else
				{
					List<MediaContent> channels = channelResponse.body();

					if (channels == null)
					{
						channels = Collections.emptyList();
					}

					handler.onSuccess(channels);
				}
			}

			@Override
			public void onFailure(@Nullable final Call<List<MediaContent>> call, @Nullable final Throwable t)
			{
				handler.onFailure(t);
			}
		});
	}

	/**
	 * Gets all of the live content with the default offset for this device.
	 *
	 * @param handler
	 * 				Receives callback if retrieving the data succeeds or fails. If data is cached then it is possible for onSuccess to be called twice - once for the
	 * 				cached data, and (if the data is stale) once for the API response.
	 */
	public void getLiveContent(@NonNull final ResponseHandler<MediaContent> handler)
	{
		int utcOffsetMillis = TimeZone.getDefault().getOffset(new Date().getTime());
		getLiveContent(utcOffsetMillis, handler);
	}

	/**
	 * Gets all of the live content with a variable offset
	 *
	 * @param utcOffsetMillis
	 * 				Offset from UTC in milliseconds
	 * @param handler
	 * 				Receives callback if retrieving the data succeeds or fails. If data is cached then it is possible for onSuccess to be called twice - once for the
	 * 				cached data, and (if the data is stale) once for the API response.
	 */
	public void getLiveContent(@NonNull final int utcOffsetMillis, @NonNull final ResponseHandler<MediaContent> handler)
	{
		final boolean hasCachedData = liveCache != null;

		// Return cached data immediately if it is present
		if (hasCachedData)
		{
			handler.onSuccess(Collections.unmodifiableList(liveCache));

			// Only perform a network request if the cached data is stale
			if (System.currentTimeMillis() - lastLiveFetchTime < LIVE_CACHE_EXPIRY_TIME)
			{
				return;
			}
		}

		// Put into the format the Lush API expects
		String offsetString = String.format("%d minutes", utcOffsetMillis / 1000 / 60);
		Call<List<MediaContent>> playlistCall = api.getPlaylist(offsetString);

		playlistCall.enqueue(new Callback<List<MediaContent>>()
		{
			@Override
			public void onResponse(@NonNull final Call<List<MediaContent>> call, @NonNull final Response<List<MediaContent>> mediaResponse)
			{
				if (!mediaResponse.isSuccessful())
				{
					onFailure(call, null);
				}
				else
				{
					List<MediaContent> mediaContent = mediaResponse.body();

					if (mediaContent == null)
					{
						mediaContent = Collections.emptyList();
					}

					liveCache = mediaContent;
					lastLiveFetchTime = System.currentTimeMillis();
					handler.onSuccess(Collections.unmodifiableList(liveCache));
				}
			}

			@Override
			public void onFailure(@Nullable final Call<List<MediaContent>> call, @Nullable final Throwable t)
			{
				// Only report failure if we didn't even report cache
				if (!hasCachedData)
				{
					handler.onFailure(t);
				}
			}
		});
	}

	/**
	 * Gets a specific programme
	 *
	 * @param programmeId
	 * @param handler
	 */
	public void getProgramme(@NonNull final String programmeId, @NonNull final ResponseHandler<Programme> handler)
	{
		Call<List<Programme>> programmeCall = api.getProgramme(programmeId);

		programmeCall.enqueue(new Callback<List<Programme>>()
		{
			@Override
			public void onResponse(Call<List<Programme>> call, Response<List<Programme>> programmeResponse)
			{
				if (!programmeResponse.isSuccessful())
				{
					handler.onFailure(null);
				}
				else
				{
					List<Programme> programmes = programmeResponse.body();

					if (programmes == null)
					{
						programmes = Collections.emptyList();
					}

					handler.onSuccess(programmes);
				}
			}

			@Override
			public void onFailure(Call<List<Programme>> call, Throwable t)
			{
				handler.onFailure(t);
			}
		});
	}
}
