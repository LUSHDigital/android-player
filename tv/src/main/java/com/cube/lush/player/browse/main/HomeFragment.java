package com.cube.lush.player.browse.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.cube.lush.player.DiffingAdapter;
import com.cube.lush.player.browse.BaseMediaBrowseFragment;
import com.cube.lush.player.browse.MediaPresenter;
import com.cube.lush.player.handler.ResponseHandler;
import com.cube.lush.player.manager.MediaManager;
import com.cube.lush.player.model.MediaContent;
import com.cube.lush.player.util.MediaSorter;

import java.util.List;

/**
 * Fragment shown on the launch page of the app when the "Home" menu item is selected.
 *
 * Created by tim on 30/11/2016.
 */
public class HomeFragment extends BaseMediaBrowseFragment
{
	/**
	 * Use a {@link DiffingAdapter} so the grid will smoothly update when changes occur.
	 */
	private DiffingAdapter<MediaContent> mediaAdapter = new DiffingAdapter<>(new MediaPresenter());

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		mediaAdapter.setEqualityTester(new DiffingAdapter.EqualityTester<MediaContent>()
		{
			@Override
			public boolean isEqual(MediaContent t1, MediaContent t2)
			{
				return t1.getId().equals(t2.getId());
			}
		});
		setAdapter(mediaAdapter);
	}

	@Override
	protected void fetchData()
	{
		MediaManager.getInstance().getMedia(new ResponseHandler<MediaContent>()
		{
			@Override public void onSuccess(@NonNull final List<MediaContent> items)
			{
				setLoadingFinished(false);
				MediaSorter.MOST_RECENT_FIRST.sort(items);
				mediaAdapter.setItems(items);
			}

			@Override public void onFailure(@Nullable Throwable t)
			{
				setLoadingFinished(true);
				mediaAdapter.clear();
			}
		});
	}
}
