package com.cube.lush.player;

import android.os.Bundle;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;

import com.cube.lush.player.model.Channel;
import com.cube.lush.player.model.VideoContent;
import com.cube.lush.player.presenter.MediaPresenter;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tim on 24/11/2016.
 */
public class ChannelFragment extends LushBrowseFragment
{
	private Channel mChannel;
	private ArrayObjectAdapter mMediaAdapter;

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		initialiseData();
		getVideos();
	}

	@Override
	protected void initialiseUI()
	{
		super.initialiseUI();
		mChannel = (Channel) getActivity().getIntent().getSerializableExtra(ChannelActivity.EXTRA_CHANNEL);
	}

	private void initialiseData()
	{
		// Setup "TV" menu item
		mMediaAdapter = new ArrayObjectAdapter(new MediaPresenter());
		ListRow tvRow = new ListRow(new HeaderItem("TV"), mMediaAdapter);

		// Setup "Radio" menu item
		ListRow radioRow = new ListRow(new HeaderItem("Radio"), mMediaAdapter);

		// Create and populate the main adapter
		ArrayObjectAdapter mainAdapter = new ArrayObjectAdapter(new ListRowPresenter());
		mainAdapter.addAll(0, Arrays.asList(tvRow, radioRow));
        setAdapter(mainAdapter);
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
