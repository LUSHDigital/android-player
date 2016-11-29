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
import com.cube.lush.player.model.RadioContent;
import com.cube.lush.player.model.VideoContent;
import com.cube.lush.player.presenter.MediaPresenter;
import com.cube.lush.player.util.MediaSorter;

import java.util.Arrays;
import java.util.List;

/**
 * Created by tim on 24/11/2016.
 */
public class ChannelFragment extends LushBrowseFragment
{
	private ArrayObjectAdapter tvAdapter;
	private ArrayObjectAdapter radioAdapter;

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		initialiseData();
		getTVContent();
		getRadioContent();
	}

	private void initialiseData()
	{
		// Setup "TV" menu item
		tvAdapter = new ArrayObjectAdapter(new MediaPresenter());
		ListRow tvRow = new ListRow(new HeaderItem("TV"), tvAdapter);

		// Setup "Radio" menu item
		radioAdapter = new ArrayObjectAdapter(new MediaPresenter());
		ListRow radioRow = new ListRow(new HeaderItem("Radio"), radioAdapter);

		// Create and populate the main adapter
		ArrayObjectAdapter mainAdapter = new ArrayObjectAdapter(new ListRowPresenter());
		mainAdapter.addAll(0, Arrays.asList(tvRow, radioRow));
        setAdapter(mainAdapter);
	}

	private void getTVContent()
	{
		// TODO: Change to get TV videos for the given channel
		MediaManager.getInstance().getVideos(new ResponseHandler<VideoContent>()
		{
			@Override public void onSuccess(@NonNull List<VideoContent> items)
			{
				MediaSorter.MOST_RECENT_FIRST.sort(items);

				tvAdapter.clear();
				tvAdapter.addAll(0, items);
			}

			@Override public void onFailure(@Nullable Throwable t)
			{
				tvAdapter.clear();
			}
		});
	}

	private void getRadioContent()
	{
		// TODO: Change to get Radio videos for the given channel
		MediaManager.getInstance().getRadios(new ResponseHandler<RadioContent>()
		{
			@Override public void onSuccess(@NonNull List<RadioContent> items)
			{
				MediaSorter.MOST_RECENT_FIRST.sort(items);

				radioAdapter.clear();
				radioAdapter.addAll(0, items);
			}

			@Override public void onFailure(@Nullable Throwable t)
			{
				radioAdapter.clear();
			}
		});
	}
}
