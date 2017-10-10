package com.cube.lush.player.tv.details;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v17.leanback.app.BrowseFragment;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;

import com.cube.lush.player.R;
import com.cube.lush.player.tv.adapter.BasicMainFragmentAdapter;
import com.cube.lush.player.tv.playback.PlaybackFragment;

/**
 * Displays details and a preview of the current live Lush playlist.
 *
 * @author Jamie Cruwys
 */
public class OffAirFragment extends PlaybackFragment implements BrowseFragment.MainFragmentAdapterProvider
{
	@SuppressWarnings("HardCodedStringLiteral")
	private static final String OFFAIR_FRAGMENT_TAG = "offair";

	public static void show(FragmentManager fragmentManager, View parentView)
	{
		fragmentManager.executePendingTransactions();
		if (parentView != null && fragmentManager.findFragmentByTag(OFFAIR_FRAGMENT_TAG) == null)
		{
			fragmentManager.beginTransaction().add(parentView.getId(), new OffAirFragment(), OFFAIR_FRAGMENT_TAG).commit();
		}
	}

	public static void hide(FragmentManager fragmentManager)
	{
		if (fragmentManager.isDestroyed())
		{
			return;
		}

		fragmentManager.executePendingTransactions();
		Fragment offAirFragment = fragmentManager.findFragmentByTag(OFFAIR_FRAGMENT_TAG);
		if (offAirFragment != null)
		{
			fragmentManager.beginTransaction().remove(offAirFragment).commitAllowingStateLoss();
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		getBrightcoveVideoView().setMediaController((MediaController)null);

		View view = getView();

		if (view != null)
		{
			View textOverlayView = view.findViewById(R.id.text_overlay);

			if (textOverlayView instanceof TextView)
			{
				TextView textOverlay = (TextView) textOverlayView;
				textOverlay.setVisibility(View.VISIBLE);
				textOverlay.setText(R.string.no_live_broadcast);
			}
		}
	}

	@Override
	public BrowseFragment.MainFragmentAdapter getMainFragmentAdapter()
	{
		return new BasicMainFragmentAdapter<>(this);
	}
}