package com.cube.lush.player.manager;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.cube.lush.player.api.LushAPI;
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
	@Getter(lazy = true) private static final MediaManager instance = new MediaManager();

	public interface ResponseHandler<T>
	{
		void onSuccess(@Nullable List<T> items);
		void onFailure(@Nullable Throwable t);
	}

	/**
	 * Gets all of the videos and radios from the API
	 * @param api to use
	 * @param handler to handle the response
	 */
	public void getMedia(@NonNull final LushAPI api, @NonNull final ResponseHandler<MediaContent> handler)
	{
		final List<MediaContent> list = Collections.synchronizedList(new ArrayList<MediaContent>());
		final CountDownLatch countdown = new CountDownLatch(2);

		getVideos(api, new ResponseHandler<VideoContent>()
		{
			@Override public void onSuccess(List<VideoContent> items)
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

		getRadios(api, new ResponseHandler<RadioContent>()
		{
			@Override public void onSuccess(List<RadioContent> items)
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
	 * Get all of the videos
	 * @param api to use
	 * @param handler to handle the response
	 */
	public void getVideos(@NonNull final LushAPI api, @NonNull final ResponseHandler<VideoContent> handler)
	{
		final Call<List<VideoContent>> videoCall = api.listVideos();
		videoCall.enqueue(new Callback<List<VideoContent>>()
		{
			@Override public void onResponse(Call<List<VideoContent>> call, Response<List<VideoContent>> videoResponse)
			{
				if (!videoResponse.isSuccessful())
				{
					handler.onFailure(null);
				}

				handler.onSuccess(videoResponse.body());
			}

			@Override public void onFailure(Call<List<VideoContent>> call, Throwable t)
			{
				handler.onFailure(t);
			}
		});
	}

	/**
	 * Get all of the radio videos
	 * @param api to use
	 * @param handler to handle the response
	 */
	public void getRadios(@NonNull final LushAPI api, @NonNull final ResponseHandler<RadioContent> handler)
	{
		final Call<List<RadioContent>> radioCall = api.listRadios();
		radioCall.enqueue(new Callback<List<RadioContent>>()
		{
			@Override public void onResponse(final Call<List<RadioContent>> call, final Response<List<RadioContent>> radioResponse)
			{
				if (!radioResponse.isSuccessful())
				{
					handler.onFailure(null);
				}

				handler.onSuccess(radioResponse.body());
			}

			@Override public void onFailure(Call<List<RadioContent>> call, Throwable t)
			{
				handler.onFailure(t);
			}
		});
	}

	/**
	 * Gets all of the channel content
	 * @param api to use
	 * @param channel that you want content for
	 * @param handler to handle the result of getting the content
	 *
	 * @return
	 */
	public void getChannelContent(@NonNull final LushAPI api, @NonNull final Channel channel, @NonNull final ResponseHandler<MediaContent> handler)
	{
		Call<List<MediaContent>> channelCall = api.getChannel(channel.getId());

		channelCall.enqueue(new Callback<List<MediaContent>>()
		{
			@Override public void onResponse(Call<List<MediaContent>> call, Response<List<MediaContent>> channelResponse)
			{
				if (!channelResponse.isSuccessful())
				{
					handler.onFailure(null);
				}

				handler.onSuccess(channelResponse.body());
			}

			@Override public void onFailure(Call<List<MediaContent>> call, Throwable t)
			{
				handler.onFailure(t);
			}
		});
	}

	/**
	 * Gets all of the live content
	 */
	public void getLiveContent(@NonNull final LushAPI api, @NonNull final String offset, @NonNull final ResponseHandler<MediaContent> handler)
	{
		Call<List<MediaContent>> playlistCall = api.getPlaylist(offset);

		playlistCall.enqueue(new Callback<List<MediaContent>>()
		{
			@Override public void onResponse(Call<List<MediaContent>> call, Response<List<MediaContent>> mediaResponse)
			{
				if (!mediaResponse.isSuccessful())
				{
					handler.onFailure(null);
				}

				handler.onSuccess(mediaResponse.body());
			}

			@Override public void onFailure(Call<List<MediaContent>> call, Throwable t)
			{
				handler.onFailure(t);
			}
		});
	}
}