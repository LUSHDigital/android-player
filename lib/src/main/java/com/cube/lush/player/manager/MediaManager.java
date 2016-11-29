package com.cube.lush.player.manager;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.cube.lush.player.api.LushAPI;
import com.cube.lush.player.handler.ResponseHandler;
import com.cube.lush.player.model.Channel;
import com.cube.lush.player.model.MediaContent;
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
 * @author Jamie Cruwys
 * @project lush-player-android-client
 */
public class MediaManager
{
	@Getter private static MediaManager instance;
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
			@Override public void onSuccess(@NonNull List<VideoContent> items)
			{
				list.addAll(items);

				countdown.countDown();

				if (countdown.getCount() == 0)
				{
					handler.onSuccess(list);
				}
			}

			@Override public void onFailure(@Nullable Throwable t)
			{
				handler.onFailure(t);
			}
		});

		getRadios(new ResponseHandler<RadioContent>()
		{
			@Override public void onSuccess(@NonNull List<RadioContent> items)
			{
				list.addAll(items);

				countdown.countDown();

				if (countdown.getCount() == 0)
				{
					handler.onSuccess(list);
				}
			}

			@Override public void onFailure(@Nullable Throwable t)
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
		final Call<List<VideoContent>> videoCall = api.listVideos();
		videoCall.enqueue(new Callback<List<VideoContent>>()
		{
			@Override public void onResponse(@NonNull final Call<List<VideoContent>> call, @NonNull final Response<List<VideoContent>> videoResponse)
			{
				if (!videoResponse.isSuccessful())
				{
					handler.onFailure(null);
				}

				handler.onSuccess(videoResponse.body());
			}

			@Override public void onFailure(@Nullable final Call<List<VideoContent>> call, @Nullable final Throwable t)
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
		final Call<List<RadioContent>> radioCall = api.listRadios();
		radioCall.enqueue(new Callback<List<RadioContent>>()
		{
			@Override public void onResponse(@NonNull final Call<List<RadioContent>> call, @NonNull final Response<List<RadioContent>> radioResponse)
			{
				if (!radioResponse.isSuccessful())
				{
					handler.onFailure(null);
				}

				handler.onSuccess(radioResponse.body());
			}

			@Override public void onFailure(@Nullable final Call<List<RadioContent>> call, @Nullable final Throwable t)
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
	public void getChannelContent(@NonNull final Channel channel, @NonNull final ResponseHandler<MediaContent> handler)
	{
		getChannelContent(channel.getId(), handler);
	}

	/**
	 * Gets all of the channel content for a given channel id
	 *
	 * @param channelId
	 * @param handler
	 */
	public void getChannelContent(@NonNull final String channelId, @NonNull final ResponseHandler<MediaContent> handler)
	{
		Call<List<MediaContent>> channelCall = api.getChannel(channelId);

		channelCall.enqueue(new Callback<List<MediaContent>>()
		{
			@Override public void onResponse(@NonNull final Call<List<MediaContent>> call, @NonNull final Response<List<MediaContent>> channelResponse)
			{
				if (!channelResponse.isSuccessful())
				{
					handler.onFailure(null);
				}

				handler.onSuccess(channelResponse.body());
			}

			@Override public void onFailure(@Nullable final Call<List<MediaContent>> call, @Nullable final Throwable t)
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
			@Override public void onResponse(@NonNull final Call<List<MediaContent>> call, @NonNull final Response<List<MediaContent>> mediaResponse)
			{
				if (!mediaResponse.isSuccessful())
				{
					handler.onFailure(null);
				}

				handler.onSuccess(mediaResponse.body());
			}

			@Override public void onFailure(@Nullable final Call<List<MediaContent>> call, @Nullable final Throwable t)
			{
				handler.onFailure(t);
			}
		});
	}
}