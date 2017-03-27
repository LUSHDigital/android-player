package com.cube.lush.player.mobile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.FrameLayout;

import com.cube.lush.player.mobile.base.BaseMobileActivity;
import com.cube.lush.player.mobile.channels.ChannelsFragment;
import com.cube.lush.player.mobile.events.EventsFragment;
import com.cube.lush.player.mobile.home.HomeFragment;
import com.cube.lush.player.mobile.live.LiveFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.cube.lush.player.R;
import com.cube.lush.player.mobile.search.SearchFragment;

public class MainActivity extends BaseMobileActivity implements com.cube.lush.player.mobile.nav.BottomNavigationView.TabSelectedListener
{
    @BindView(R.id.navigation)
	com.cube.lush.player.mobile.nav.BottomNavigationView navigation;

    @BindView(R.id.container)
    FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_phone);
        ButterKnife.bind(this);

		navigation.setTabSelectedListener(this);
		navigation.onHomeTabClicked();
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

	@Override public void onHomeTabClicked()
	{
		showFragment(HomeFragment.newInstance());
	}

	@Override public void onLiveTabClicked()
	{
		showFragment(LiveFragment.newInstance());
	}

	@Override public void onChannelsTabClicked()
	{
		showFragment(ChannelsFragment.newInstance());
	}

	@Override public void onEventsTabClicked()
	{
		showFragment(EventsFragment.newInstance());
	}

	@Override public void onSearchTabClicked()
	{
		showFragment(SearchFragment.newInstance());
	}
}