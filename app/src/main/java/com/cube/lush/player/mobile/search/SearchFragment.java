package com.cube.lush.player.mobile.search;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cube.lush.player.R;

public class SearchFragment extends Fragment
{
	public SearchFragment()
	{
		// Required empty public constructor
	}

	public static SearchFragment newInstance()
	{
		SearchFragment fragment = new SearchFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_search, container, false);
	}
}