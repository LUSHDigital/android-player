package com.cube.lush.player.mobile.live;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cube.lush.player.R;

import uk.co.jamiecruwys.StatefulFragment;

public class LiveFragment extends StatefulFragment
{
	public LiveFragment()
	{
		// Required empty public constructor
	}

	public static LiveFragment newInstance()
	{
		LiveFragment fragment = new LiveFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override protected int provideContentLayout()
	{
		return R.layout.mobile_fragment_live;
	}

	@Override protected int provideEmptyLayout()
	{
		return R.layout.mobile_empty;
	}

	@Override protected int provideLoadingLayout()
	{
		return R.layout.mobile_loading;
	}

	@Override protected int provideErrorLayout()
	{
		return R.layout.mobile_error;
	}
}