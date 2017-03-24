package com.cube.lush.player.mobile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cube.lush.player.R;

/**
 * Created by Jamie Cruwys of 3 SIDED CUBE on 24/03/2017.
 */
public class SpinnerFragment extends Fragment
{
	public static final String SPINNER_FRAGMENT_TAG = "spinner";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.fragment_loading, container, false);
	}
}