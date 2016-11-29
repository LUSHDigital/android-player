package com.cube.lush.player;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;

import com.cube.lush.player.manager.MediaManager;
import com.cube.lush.player.model.Channel;
import com.cube.lush.player.model.MediaContent;
import com.cube.lush.player.presenter.ChannelPresenter;
import com.cube.lush.player.presenter.MediaPresenter;

import java.util.Arrays;
import java.util.List;

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
		ListRow liveRow = new ListRow(new HeaderItem("Live"), mMediaAdapter);

		// Setup "Channels" menu item
		ArrayObjectAdapter channelAdapter = new ArrayObjectAdapter(new ChannelPresenter());
		channelAdapter.addAll(0, Arrays.asList(Channel.values()));
		ListRow channelsRow = new ListRow(new HeaderItem("Channels"), channelAdapter);

		// Create and populate the main adapter
		ArrayObjectAdapter mainAdapter = new ArrayObjectAdapter(new ListRowPresenter());
		mainAdapter.addAll(0, Arrays.asList(homeRow, liveRow, channelsRow));
        setAdapter(mainAdapter);
	}

	private void getVideos()
	{
		MediaManager.getInstance().getVideos(MainApplication.getAPI(), new MediaManager.ResponseHandler()
		{
			@Override public void onSuccess(List<? extends MediaContent> content)
			{
				mMediaAdapter.clear();
				mMediaAdapter.addAll(0, content);
			}

			@Override public void onFailure(@Nullable Throwable t)
			{
				mMediaAdapter.clear();
			}
		});
	}
}
