package com.cube.lush.player;

import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.app.VerticalGridFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.VerticalGridPresenter;

import com.cube.lush.player.adapter.BasicMainFragmentAdapter;

/**
 * Created by tim on 30/11/2016.
 */
public class MediaBrowseFragment extends VerticalGridFragment implements BrowseFragment.MainFragmentAdapterProvider
{
	private BrowseFragment.MainFragmentAdapter<MediaBrowseFragment> mMainFragmentAdapter;
	private ArrayObjectAdapter mMediaAdapter;

	public MediaBrowseFragment()
	{
		VerticalGridPresenter presenter = new VerticalGridPresenter();
		presenter.setNumberOfColumns(3);
		setGridPresenter(presenter);
	}

	@Override
	public BrowseFragment.MainFragmentAdapter<MediaBrowseFragment> getMainFragmentAdapter()
	{
		if (mMainFragmentAdapter == null)
		{
			mMainFragmentAdapter = new BasicMainFragmentAdapter<>(this);
		}
		return mMainFragmentAdapter;
	}
}
