package com.cube.lush.player.manager;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.cube.lush.player.api.LushAPI;
import com.cube.lush.player.handler.ResponseHandler;
import com.cube.lush.player.model.CategoryContentType;
import com.cube.lush.player.model.Channel;
import com.cube.lush.player.model.MediaContent;
import com.cube.lush.player.model.Programme;
import com.cube.lush.player.model.RadioContent;
import com.cube.lush.player.model.VideoContent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import lombok.Getter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Manages requests for media, returning it to the requester from the most appropriate source. Currently gets data from API without caching, but in future (and
 * especially for phone app) should support caching.
 *
 * @author Jamie Cruwys
 * @project lush-player-android-client
 */
public class MediaManager
{
	@Getter
	private static MediaManager instance;
	private LushAPI api;

	public static void initialise(@NonNull LushAPI api)
	{
		instance = new MediaManager(api);
	}

	private MediaManager(@NonNull LushAPI api)
	{
		this.api = api;
	}

	/**
	 * Gets all of the media content, which is videos and radios
	 *
	 * @param handler
	 */
	public void getMedia(@NonNull final ResponseHandler<MediaContent> handler)
	{
		final List<MediaContent> list = Collections.synchronizedList(new ArrayList<MediaContent>());
		final CountDownLatch countdown = new CountDownLatch(2);

		getVideos(new ResponseHandler<VideoContent>()
		{
			@Override
			public void onSuccess(@NonNull List<VideoContent> items)
			{
				if (!items.isEmpty())
				{
					list.addAll(items);
				}

				countdown.countDown();

				if (countdown.getCount() == 0)
				{
					handler.onSuccess(list);
				}
			}

			@Override
			public void onFailure(@Nullable Throwable t)
			{
				handler.onFailure(t);
			}
		});

		getRadios(new ResponseHandler<RadioContent>()
		{
			@Override
			public void onSuccess(@NonNull List<RadioContent> items)
			{
				if (!items.isEmpty())
				{
					list.addAll(items);
				}

				countdown.countDown();

				if (countdown.getCount() == 0)
				{
					handler.onSuccess(list);
				}
			}

			@Override
			public void onFailure(@Nullable Throwable t)
			{
				handler.onFailure(t);
			}
		});
	}

	/**
	 * Gets all of the video content
	 *
	 * @param handler
	 */
	public void getVideos(@NonNull final ResponseHandler<VideoContent> handler)
	{
		final Call<List<VideoContent>> videoCall = api.getVideos();
		videoCall.enqueue(new Callback<List<VideoContent>>()
		{
			@Override
			public void onResponse(@NonNull final Call<List<VideoContent>> call, @NonNull final Response<List<VideoContent>> videoResponse)
			{
				if (!videoResponse.isSuccessful())
				{
					handler.onFailure(null);
				}

				List<VideoContent> videos = videoResponse.body();

				if (videos == null)
				{
					videos = Collections.emptyList();
				}

				handler.onSuccess(videos);
			}

			@Override
			public void onFailure(@Nullable final Call<List<VideoContent>> call, @Nullable final Throwable t)
			{
				handler.onFailure(t);
			}
		});
	}

	/**
	 * Gets all of the radio content
	 *
	 * @param handler
	 */
	public void getRadios(@NonNull final ResponseHandler<RadioContent> handler)
	{
		final Call<List<RadioContent>> radioCall = api.getRadios();
		radioCall.enqueue(new Callback<List<RadioContent>>()
		{
			@Override
			public void onResponse(@NonNull final Call<List<RadioContent>> call, @NonNull final Response<List<RadioContent>> radioResponse)
			{
				if (!radioResponse.isSuccessful())
				{
					handler.onFailure(null);
				}

				List<RadioContent> radios = radioResponse.body();

				if (radios == null)
				{
					radios = Collections.emptyList();
				}

				handler.onSuccess(radios);
			}

			@Override
			public void onFailure(@Nullable final Call<List<RadioContent>> call, @Nullable final Throwable t)
			{
				handler.onFailure(t);
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
		String contentTypeName = contentType == null ? null : contentType.getName();
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

				List<MediaContent> channels = channelResponse.body();

				if (channels == null)
				{
					channels = Collections.emptyList();
				}

				handler.onSuccess(channels);
			}

			@Override
			public void onFailure(@Nullable final Call<List<MediaContent>> call, @Nullable final Throwable t)
			{
				handler.onFailure(t);
			}
		});
	}

	/**
	 * Gets all of the live content with in offset of 0
	 *
	 * @param handler
	 */
	public void getLiveContent(@NonNull final ResponseHandler<MediaContent> handler)
	{
		getLiveContent("0", handler);
	}

	/**
	 * Gets all of the live content with a variable offset
	 *
	 * @param offset
	 * @param handler
	 */
	public void getLiveContent(@NonNull final String offset, @NonNull final ResponseHandler<MediaContent> handler)
	{
		Call<List<MediaContent>> playlistCall = api.getPlaylist(offset);

		playlistCall.enqueue(new Callback<List<MediaContent>>()
		{
			@Override
			public void onResponse(@NonNull final Call<List<MediaContent>> call, @NonNull final Response<List<MediaContent>> mediaResponse)
			{
				if (!mediaResponse.isSuccessful())
				{
					handler.onFailure(null);
				}

				List<MediaContent> mediaContent = mediaResponse.body();

				if (mediaContent == null)
				{
					mediaContent = Collections.emptyList();
				}

				handler.onSuccess(mediaContent);
			}

			@Override
			public void onFailure(@Nullable final Call<List<MediaContent>> call, @Nullable final Throwable t)
			{
				handler.onFailure(t);
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

				List<Programme> programmes = programmeResponse.body();

				if (programmes == null)
				{
					programmes = Collections.emptyList();
				}

				handler.onSuccess(programmes);
			}

			@Override
			public void onFailure(Call<List<Programme>> call, Throwable t)
			{
				handler.onFailure(t);
			}
		});
	}
}
