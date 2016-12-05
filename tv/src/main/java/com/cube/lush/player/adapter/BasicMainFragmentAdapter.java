package com.cube.lush.player.adapter;

import android.app.Fragment;
import android.support.v17.leanback.app.BrowseFragment;

/**
 * A very basic implementation of {@link android.support.v17.leanback.app.BrowseFragment.MainFragmentAdapter} which doesn't do anything in particular.
 *
 * Created by tim on 29/11/2016.
 */
public class BasicMainFragmentAdapter<T extends Fragment> extends BrowseFragment.MainFragmentAdapter<T>
{
	public BasicMainFragmentAdapter(T fragment)
	{
		super(fragment);
		setScalingEnabled(false);
	}

	@Override
	public boolean isScrolling()
	{
		return false;
	}

	@Override
	public void setExpand(boolean expand)
	{
	}

	@Override
	public void setEntranceTransitionState(boolean state)
	{
	}

	@Override
	public void setAlignment(int windowAlignOffsetFromTop)
	{
	}

	@Override
	public boolean onTransitionPrepare()
	{
		return false;
	}

	@Override
	public void onTransitionStart()
	{
	}

	@Override
	public void onTransitionEnd()
	{
	}
}
