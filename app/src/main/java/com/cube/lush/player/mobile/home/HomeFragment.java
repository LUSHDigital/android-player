package com.cube.lush.player.mobile.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cube.lush.player.R;

public class HomeFragment extends Fragment
{
	public HomeFragment()
	{
		// Required empty public constructor
	}

	public static HomeFragment newInstance()
	{
		HomeFragment fragment = new HomeFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.mobile_fragment_home, container, false);
	}
}