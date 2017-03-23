package com.cube.lush.player.mobile.nav;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.cube.lush.player.mobile.R;

import lombok.AllArgsConstructor;

/**
 * Created by Jamie Cruwys of 3 SIDED CUBE on 23/03/2017.
 */
@AllArgsConstructor
public class BottomNavigationItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener
{
	private @NonNull FrameLayout container;
	private @NonNull FragmentManager fragmentManager;

	@Override public boolean onNavigationItemSelected(@NonNull MenuItem item)
	{
		int id = item.getItemId();

		if (id == R.id.navigation_home)
		{
			return true;
		}
		else if (id == R.id.navigation_live)
		{

			return true;
		}
		else if (id == R.id.navigation_channels)
		{

			return true;
		}
		else if (id == R.id.navigation_events)
		{

			return true;
		}
		else if (id == R.id.navigation_search)
		{

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
}