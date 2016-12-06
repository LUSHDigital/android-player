package com.cube.lush.player;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v17.leanback.widget.ArrayObjectAdapter;

import com.cube.lush.player.handler.ResponseHandler;
import com.cube.lush.player.manager.MediaManager;
import com.cube.lush.player.model.MediaContent;
import com.cube.lush.player.presenter.MediaPresenter;
import com.cube.lush.player.util.MediaSorter;

import java.util.List;

/**
 * Fragment shown on the launch page of the app when the "Home" menu item is selected.
 *
 * Created by tim on 30/11/2016.
 */
public class HomeFragment extends MediaBrowseFragment
{
	private ArrayObjectAdapter mMediaAdapter;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		mMediaAdapter = new ArrayObjectAdapter(new MediaPresenter());
		setAdapter(mMediaAdapter);
	}

	@Override
	protected void fetchData()
	{
		MediaManager.getInstance().getMedia(new ResponseHandler<MediaContent>()
		{
			@Override public void onSuccess(@NonNull List<MediaContent> items)
			{
				setLoadingFinished(false);
				items = MediaSorter.MOST_RECENT_FIRST.sort(items);
				mMediaAdapter.clear();
				mMediaAdapter.addAll(0, items);
			}

			@Override public void onFailure(@Nullable Throwable t)
			{
				setLoadingFinished(true);
				mMediaAdapter.clear();
			}
		});
	}
}
