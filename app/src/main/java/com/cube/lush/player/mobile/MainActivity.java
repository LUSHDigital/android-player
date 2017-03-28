package com.cube.lush.player.mobile;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.widget.FrameLayout;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.cube.lush.player.mobile.base.BaseMobileActivity;
import com.cube.lush.player.mobile.channels.ChannelsFragment;
import com.cube.lush.player.mobile.events.EventsFragment;
import com.cube.lush.player.mobile.home.HomeFragment;
import com.cube.lush.player.mobile.live.LiveFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.cube.lush.player.R;
import com.cube.lush.player.mobile.search.SearchFragment;

import java.util.ArrayList;

public class MainActivity extends BaseMobileActivity implements AHBottomNavigation.OnTabSelectedListener
{
    @BindView(R.id.bottom_navigation)
	AHBottomNavigation bottomNavigation;

    @BindView(R.id.container)
    FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_phone);
        ButterKnife.bind(this);

		setupNavigation();
    }

    private void setupNavigation()
	{
		ArrayList<AHBottomNavigationItem> items = new ArrayList<>();
		items.add(new AHBottomNavigationItem(R.string.title_search, R.drawable.ic_search_black_24dp, android.R.color.black));
		items.add(new AHBottomNavigationItem(R.string.title_home, R.drawable.ic_home, android.R.color.black));
		items.add(new AHBottomNavigationItem(R.string.title_live, R.drawable.ic_live, android.R.color.black));

		for (AHBottomNavigationItem item : items)
		{
			bottomNavigation.addItem(item);
		}

		// Colour for selected item
		bottomNavigation.setAccentColor(Color.BLACK);

		// Colour for unselected items
		bottomNavigation.setInactiveColor(ContextCompat.getColor(this, R.color.dark_grey));

		// Background colour
		bottomNavigation.setDefaultBackgroundColor(Color.WHITE);

		// Forces titles to show, which the standard bottom bar does not support
		bottomNavigation.setForceTitlesDisplay(true);

		// Tab selection
		bottomNavigation.setOnTabSelectedListener(this);

		// Auto selected home
		bottomNavigation.setCurrentItem(0);
	}

	@Override public boolean onTabSelected(int position, boolean wasSelected)
	{
		switch (position)
		{
			case 0:
				showFragment(HomeFragment.newInstance());
				return true;
			case 1:
				showFragment(LiveFragment.newInstance());
				return true;
			case 2:
				showFragment(ChannelsFragment.newInstance());
				return true;
			case 3:
				showFragment(EventsFragment.newInstance());
				return true;
			case 4:
				showFragment(EventsFragment.newInstance());
				return true;
			case 5:
				showFragment(SearchFragment.newInstance());
				return true;
			default:
				throw new RuntimeException("Unknown tab selected");
		}
	}

    public void showFragment(@NonNull Fragment fragment)
	{
		showFragment(fragment, null);
	}

	public void showFragment(@NonNull Fragment fragment, @Nullable String tag)
	{
		FragmentManager fragmentManager = getSupportFragmentManager();

		fragmentManager.beginTransaction()
			.replace(container.getId(), fragment, tag)
			.commit();
	}

	public void showLoading()
	{
		getSupportFragmentManager().beginTransaction()
			.add(container.getId(), new SpinnerFragment(), SpinnerFragment.SPINNER_FRAGMENT_TAG)
			.commit();
	}

	public void hideLoading()
	{
		FragmentManager fragmentManager = getSupportFragmentManager();

		if (fragmentManager.isDestroyed())
		{
			return;
		}

		Fragment fragment = fragmentManager.findFragmentByTag(SpinnerFragment.SPINNER_FRAGMENT_TAG);

		if (fragment != null)
		{
			fragmentManager.beginTransaction()
				.remove(fragment)
				.commit();
		}

	}
}