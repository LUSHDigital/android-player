package com.cube.lush.player.manager;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.cube.lush.player.api.LushAPI;
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

	public interface MediaResponseHandler
	{
		void onSuccess(List<? extends MediaContent> content);
		void onFailure(@Nullable Throwable t);
	}

	public void getVideos(@NonNull final LushAPI api, @NonNull final MediaResponseHandler handler)
	{
		final List<MediaContent> contents = Collections.synchronizedList(new ArrayList<MediaContent>());
		final int NUMBER_OF_API_CALLS = 2;
		final CountDownLatch countdown = new CountDownLatch(NUMBER_OF_API_CALLS);

		final Call<List<VideoContent>> videoCall = api.listVideos();
		videoCall.enqueue(new Callback<List<VideoContent>>()
		{
			@Override public void onResponse(Call<List<VideoContent>> call, Response<List<VideoContent>> videoResponse)
			{
				if (!videoResponse.isSuccessful())
				{
					handler.onFailure(null);
				}
				else
				{
					contents.addAll(videoResponse.body());
					countdown.countDown();
				}

				if (countdown.getCount() == 0)
				{
					handler.onSuccess(videoResponse.body());
				}
			}

			@Override public void onFailure(Call<List<VideoContent>> call, Throwable t)
			{
				handler.onFailure(t);
			}
		});

		Call<List<RadioContent>> radioCall = api.listRadios();
		radioCall.enqueue(new Callback<List<RadioContent>>()
		{
			@Override public void onResponse(Call<List<RadioContent>> call, final Response<List<RadioContent>> radioResponse)
			{
				if (!radioResponse.isSuccessful())
				{
					handler.onFailure(null);
				}
				else
				{
					contents.addAll(radioResponse.body());
					countdown.countDown();
				}

				if (countdown.getCount() == 0)
				{
					handler.onSuccess(radioResponse.body());
				}
			}

			@Override public void onFailure(Call<List<RadioContent>> call, Throwable t)
			{
				handler.onFailure(t);
			}
		});
	}
}