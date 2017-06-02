package com.cube.lush.player.tv.adapter;

import android.app.Fragment;
import android.support.v17.leanback.app.BrowseFragment;

/**
 * A very basic implementation of {@link BrowseFragment.MainFragmentAdapter}.
 *
 * @author Jamie Cruwys
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
