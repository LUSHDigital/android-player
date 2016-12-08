package com.cube.lush.player.details;

import android.app.Fragment;
import android.app.FragmentManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v17.leanback.app.BrowseFragment;
import android.view.View;
import android.widget.MediaController;

import com.cube.lush.player.browse.BasicMainFragmentAdapter;
import com.cube.lush.player.playback.PlaybackFragment;

/**
 * Displays details and a preview of the current live Lush playlist.
 *
 * @author Jamie Cruwys
 * @project lush-player-android-client
 */
public class OffAirFragment extends PlaybackFragment implements BrowseFragment.MainFragmentAdapterProvider
{
	private static final String OFFAIR_FRAGMENT_TAG = "offair";

	public static void show(FragmentManager fragmentManager, View parentView)
	{
		if (parentView != null && fragmentManager.findFragmentByTag(OFFAIR_FRAGMENT_TAG) == null)
		{
			fragmentManager.beginTransaction().add(parentView.getId(), new OffAirFragment(), OFFAIR_FRAGMENT_TAG).commit();
		}
	}

	public static void hide(FragmentManager fragmentManager)
	{
		fragmentManager.executePendingTransactions();
		Fragment offAirFragment = fragmentManager.findFragmentByTag(OFFAIR_FRAGMENT_TAG);
		if (offAirFragment != null)
		{
			fragmentManager.beginTransaction().remove(offAirFragment).commit();
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		getBrightcoveVideoView().setMediaController((MediaController)null);
		playFile("asset:///videos/offair.mp4");
		getBrightcoveVideoView().setOnCompletionListener(new MediaPlayer.OnCompletionListener()
		{
			@Override
			public void onCompletion(MediaPlayer mediaPlayer)
			{
				getBrightcoveVideoView().start();
			}
		});
	}

	@Override
	public BrowseFragment.MainFragmentAdapter getMainFragmentAdapter()
	{
		return new BasicMainFragmentAdapter<>(this);
	}
}
