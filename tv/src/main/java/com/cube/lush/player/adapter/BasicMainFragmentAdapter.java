package com.cube.lush.player.adapter;

import android.app.Fragment;
import android.support.v17.leanback.app.BrowseFragment;

/**
 * Created by tim on 29/11/2016.
 */
public class BasicMainFragmentAdapter<T extends Fragment> extends BrowseFragment.MainFragmentAdapter<T>
{
		public BasicMainFragmentAdapter(T fragment)
		{
			super(fragment);
			setScalingEnabled(true);
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
