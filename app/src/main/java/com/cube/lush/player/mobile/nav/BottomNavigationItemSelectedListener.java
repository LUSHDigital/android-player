package com.cube.lush.player.mobile.nav;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.cube.lush.player.R;
import com.cube.lush.player.mobile.channels.ChannelsFragment;
import com.cube.lush.player.mobile.events.EventsFragment;
import com.cube.lush.player.mobile.home.HomeFragment;
import com.cube.lush.player.mobile.live.LiveFragment;
import com.cube.lush.player.mobile.search.SearchFragment;

import lombok.AllArgsConstructor;

/**
 * Created by Jamie Cruwys of 3 SIDED CUBE on 23/03/2017.
 */
@AllArgsConstructor
public class BottomNavigationItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener
{
	protected @NonNull FrameLayout container;
	protected @NonNull FragmentManager fragmentManager;

	@Override public boolean onNavigationItemSelected(@NonNull MenuItem item)
	{
		int id = item.getItemId();

		if (id == R.id.navigation_home)
		{
			show(HomeFragment.newInstance());
			return true;
		}
		else if (id == R.id.navigation_live)
		{
			show(LiveFragment.newInstance());
			return true;
		}
		else if (id == R.id.navigation_channels)
		{
			show(ChannelsFragment.newInstance());
			return true;
		}
		else if (id == R.id.navigation_events)
		{
			show(EventsFragment.newInstance());
			return true;
		}
		else if (id == R.id.navigation_search)
		{
			show(SearchFragment.newInstance());
			return true;
		}

		return false;
	}

	private void show(Fragment fragment)
	{
		if (fragmentManager == null)
		{
			throw new RuntimeException("Null fragment manager");
		}

		if (container == null)
		{
			throw new RuntimeException("Null container view");
		}

		fragmentManager.beginTransaction()
			.replace(container.getId(), fragment)
			.commitNow();
	}

	public void showDefaultItem()
	{
		show(HomeFragment.newInstance());
	}
}