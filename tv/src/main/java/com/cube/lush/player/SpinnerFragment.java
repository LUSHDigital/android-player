package com.cube.lush.player;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Displayed to the user in order to indicate that data is loading.
 *
 * @author Jamie Cruwys
 * @project lush-player-android-client
 */
public class SpinnerFragment extends Fragment
{
	private static final String SPINNER_FRAGMENT_TAG = "spinner";

	public static void show(FragmentManager fragmentManager, View parentView)
	{
		if (parentView != null && fragmentManager.findFragmentByTag(SPINNER_FRAGMENT_TAG) == null)
		{
			fragmentManager.beginTransaction().add(parentView.getId(), new SpinnerFragment(), SPINNER_FRAGMENT_TAG).commit();
		}
	}

	public static void hide(FragmentManager fragmentManager)
	{
		if (fragmentManager.isDestroyed())
		{
			return;
		}

		fragmentManager.executePendingTransactions();
		Fragment spinnerFragment = fragmentManager.findFragmentByTag(SPINNER_FRAGMENT_TAG);
		if (spinnerFragment != null)
		{
			fragmentManager.beginTransaction().remove(spinnerFragment).commitAllowingStateLoss();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.fragment_spinner, container, false);
	}
}
