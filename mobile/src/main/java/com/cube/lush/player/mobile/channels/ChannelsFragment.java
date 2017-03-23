package com.cube.lush.player.mobile.channels;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cube.lush.player.mobile.R;

public class ChannelsFragment extends Fragment
{
	public ChannelsFragment()
	{
		// Required empty public constructor
	}

	public static ChannelsFragment newInstance()
	{
		ChannelsFragment fragment = new ChannelsFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_channels, container, false);
	}
}