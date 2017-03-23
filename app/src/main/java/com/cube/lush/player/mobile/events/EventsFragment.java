package com.cube.lush.player.mobile.events;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cube.lush.player.mobile.R;

public class EventsFragment extends Fragment
{
	public EventsFragment()
	{
		// Required empty public constructor
	}

	public static EventsFragment newInstance()
	{
		EventsFragment fragment = new EventsFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_events, container, false);
	}
}