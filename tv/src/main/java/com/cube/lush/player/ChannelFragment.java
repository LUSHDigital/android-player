package com.cube.lush.player;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;

import com.cube.lush.player.manager.MediaManager;
import com.cube.lush.player.model.VideoContent;
import com.cube.lush.player.presenter.MediaPresenter;

import java.util.Arrays;
import java.util.List;

/**
 * Created by tim on 24/11/2016.
 */
public class ChannelFragment extends LushBrowseFragment
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
		MediaManager.getInstance().getVideos(new MediaManager.ResponseHandler<VideoContent>()
		{
			@Override public void onSuccess(@NonNull List<VideoContent> items)
			{
				mMediaAdapter.clear();
				mMediaAdapter.addAll(0, items);
			}

			@Override public void onFailure(@Nullable Throwable t)
			{
				mMediaAdapter.clear();
			}
		});
	}
}
