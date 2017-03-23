package com.cube.lush.player.tv.state;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.cube.lush.player.R;

/**
 * Displayed to the user in order to indicate that data is loading.
 *
 * @author Jamie Cruwys
 * @project lush-player-android-client
 */
public class ErrorFragment extends android.support.v17.leanback.app.ErrorFragment
{
	private static final String ERROR_FRAGMENT_TAG = "error";

	public static void show(FragmentManager fragmentManager, View parentView, Runnable retryAction)
	{
		fragmentManager.executePendingTransactions();
		if (parentView != null && fragmentManager.findFragmentByTag(ERROR_FRAGMENT_TAG) == null)
		{
			ErrorFragment errorFragment = new ErrorFragment();
			errorFragment.retryAction = retryAction;
			fragmentManager.beginTransaction().add(parentView.getId(), errorFragment, ERROR_FRAGMENT_TAG).commit();
		}
	}

	public static void hide(FragmentManager fragmentManager)
	{
		if (fragmentManager.isDestroyed())
		{
			return;
		}

		fragmentManager.executePendingTransactions();
		Fragment spinnerFragment = fragmentManager.findFragmentByTag(ERROR_FRAGMENT_TAG);
		if (spinnerFragment != null)
		{
			fragmentManager.beginTransaction().remove(spinnerFragment).commitAllowingStateLoss();
		}
	}

	private Runnable retryAction;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setImageDrawable(getResources().getDrawable(R.drawable.ic_cloud_off_white_48dp));
		setDefaultBackground(true);
		setMessage("Please check your network connection and try again");
		setButtonText("Retry");
		setButtonClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				retryAction.run();
			}
		});
	}
}
