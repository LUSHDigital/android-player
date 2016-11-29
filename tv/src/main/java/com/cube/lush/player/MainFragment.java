package com.cube.lush.player;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;

import com.cube.lush.player.handler.ResponseHandler;
import com.cube.lush.player.manager.MediaManager;
import com.cube.lush.player.model.Channel;
import com.cube.lush.player.model.MediaContent;
import com.cube.lush.player.presenter.ChannelPresenter;
import com.cube.lush.player.presenter.MediaPresenter;
import com.cube.lush.player.util.MediaSorter;

import java.util.Arrays;
import java.util.List;

/**
 * Created by tim on 24/11/2016.
 */
public class MainFragment extends LushBrowseFragment
{
	private ArrayObjectAdapter mediaAdapter;
	private ArrayObjectAdapter liveAdapter;

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		initialiseData();
		getMediaContent();
		getLiveContent();
	}

	private void initialiseData()
	{
		// Setup "Home" menu item
		mediaAdapter = new ArrayObjectAdapter(new MediaPresenter());
		ListRow homeRow = new ListRow(new HeaderItem("Home"), mediaAdapter);

		// Setup "Live" menu item
		liveAdapter = new ArrayObjectAdapter(new MediaPresenter());
		ListRow liveRow = new ListRow(new HeaderItem("Live"), liveAdapter);

		// Setup "Channels" menu item
		ArrayObjectAdapter channelAdapter = new ArrayObjectAdapter(new ChannelPresenter());
		channelAdapter.addAll(0, Arrays.asList(Channel.values()));
		ListRow channelsRow = new ListRow(new HeaderItem("Channels"), channelAdapter);

		// Create and populate the main adapter
		ArrayObjectAdapter mainAdapter = new ArrayObjectAdapter(new ListRowPresenter());
		mainAdapter.addAll(0, Arrays.asList(homeRow, liveRow, channelsRow));
        setAdapter(mainAdapter);
	}

	private void getMediaContent()
	{
		MediaManager.getInstance().getMedia(new ResponseHandler<MediaContent>()
		{
			@Override public void onSuccess(@NonNull List<MediaContent> items)
			{
				items = MediaSorter.MOST_RECENT_FIRST.sort(items);

				mediaAdapter.clear();
				mediaAdapter.addAll(0, items);
			}

			@Override public void onFailure(@Nullable Throwable t)
			{
				mediaAdapter.clear();
			}
		});
	}

	private void getLiveContent()
	{
		// TODO: Change to get Live Content instead of media
		MediaManager.getInstance().getMedia(new ResponseHandler<MediaContent>()
		{
			@Override public void onSuccess(@NonNull List<MediaContent> items)
			{
				items = MediaSorter.MOST_RECENT_FIRST.sort(items);

				liveAdapter.clear();
				liveAdapter.addAll(0, items);
			}

			@Override public void onFailure(@Nullable Throwable t)
			{
				liveAdapter.clear();
			}
		});
	}
}
