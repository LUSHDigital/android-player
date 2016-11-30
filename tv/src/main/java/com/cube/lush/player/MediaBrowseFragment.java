package com.cube.lush.player;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.app.VerticalGridFragment;

import com.cube.lush.player.adapter.BasicMainFragmentAdapter;
import com.cube.lush.player.presenter.GridPresenter;

/**
 * Created by tim on 30/11/2016.
 */
public class MediaBrowseFragment extends VerticalGridFragment implements BrowseFragment.MainFragmentAdapterProvider
{
	private BrowseFragment.MainFragmentAdapter<MediaBrowseFragment> mainFragmentAdapter;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setGridPresenter(new GridPresenter());
	}

	@Override
	public BrowseFragment.MainFragmentAdapter<MediaBrowseFragment> getMainFragmentAdapter()
	{
		if (mainFragmentAdapter == null)
		{
			mainFragmentAdapter = new BasicMainFragmentAdapter<>(this);
		}
		return mainFragmentAdapter;
	}
}
