package com.cube.lush.player;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v17.leanback.widget.ArrayObjectAdapter;

import com.cube.lush.player.model.VideoContent;
import com.cube.lush.player.presenter.ChannelPresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tim on 30/11/2016.
 */
public class HomeFragment extends MediaBrowseFragment
{
	private ArrayObjectAdapter mMediaAdapter;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		mMediaAdapter = new ArrayObjectAdapter(new ChannelPresenter());
		setAdapter(mMediaAdapter);
		getVideos();
	}

	private void getVideos()
	{
		MainApplication.getAPI().listVideos().enqueue(new Callback<List<VideoContent>>()
		{
			@Override public void onResponse(Call<List<VideoContent>> call, Response<List<VideoContent>> response)
			{
				if (response.isSuccessful())
				{
					List<VideoContent> videos = response.body();
					mMediaAdapter.clear();
					mMediaAdapter.addAll(0, videos);
				}
			}

			@Override public void onFailure(Call<List<VideoContent>> call, Throwable t)
			{
				mMediaAdapter.clear();
			}
		});
	}
}
