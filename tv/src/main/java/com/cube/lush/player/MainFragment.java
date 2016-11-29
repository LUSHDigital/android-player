package com.cube.lush.player;

import android.os.Bundle;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.ClassPresenterSelector;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.InvisibleRowPresenter;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.PageRow;

import com.cube.lush.player.model.Channel;
import com.cube.lush.player.model.VideoContent;
import com.cube.lush.player.presenter.ChannelPresenter;
import com.cube.lush.player.presenter.HomeRowPresenter;
import com.cube.lush.player.presenter.MediaPresenter;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tim on 24/11/2016.
 */
public class MainFragment extends LushBrowseFragment
{
	private ArrayObjectAdapter mMediaAdapter;

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		initialiseData();
		getVideos();
	}

	private void initialiseData()
	{
		// Setup "Home" menu item
		mMediaAdapter = new ArrayObjectAdapter(new MediaPresenter());
		ListRow homeRow = new ListRow(new HeaderItem("Home"), mMediaAdapter);

		// Setup "Live" menu item
		getMainFragmentRegistry().registerFragment(PageRow.class, new MediaDetailsFragmentFragmentFactory());
		PageRow liveRow = new PageRow(new HeaderItem("Live"));

		// Setup "Channels" menu item
		ArrayObjectAdapter channelAdapter = new ArrayObjectAdapter(new ChannelPresenter());
		channelAdapter.addAll(0, Arrays.asList(Channel.values()));
		ListRow channelsRow = new ListRow(new HeaderItem("Channels"), channelAdapter);

		// Create and populate the main adapter
		ClassPresenterSelector mainPresenterSelector = new ClassPresenterSelector();
		mainPresenterSelector.addClassPresenter(ListRow.class, new HomeRowPresenter());
		mainPresenterSelector.addClassPresenter(PageRow.class, new InvisibleRowPresenter());
		ArrayObjectAdapter mainAdapter = new ArrayObjectAdapter(mainPresenterSelector);
		mainAdapter.addAll(0, Arrays.asList(homeRow, liveRow, channelsRow));
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

	private static class MediaDetailsFragmentFragmentFactory extends FragmentFactory<MediaDetailsFragment>
	{
		@Override
		public MediaDetailsFragment createFragment(Object row)
		{
			return new MediaDetailsFragment();
		}
	}
}
