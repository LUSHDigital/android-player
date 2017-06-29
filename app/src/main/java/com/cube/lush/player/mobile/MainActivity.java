package com.cube.lush.player.mobile;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.cube.lush.player.R;
import com.cube.lush.player.mobile.base.BaseMobileActivity;
import com.cube.lush.player.mobile.channels.ChannelsFragment;
import com.cube.lush.player.mobile.details.DetailsFragment;
import com.cube.lush.player.mobile.events.EventsFragment;
import com.cube.lush.player.mobile.home.HomeFragment;
import com.cube.lush.player.mobile.live.LiveFragment;
import com.cube.lush.player.mobile.playback.LushPlaybackActivity;
import com.cube.lush.player.mobile.search.SearchFragment;
import com.lush.player.api.API;
import com.lush.player.api.model.Programme;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.jamiecruwys.ViewState;

/**
 * Main Activity
 *
 * @author Jamie Cruwys
 */
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
		ButterKnife.bind(this);

		setupNavigation();

		if (savedInstanceState == null)
		{
			selectTab(LushTab.HOME);
		}

		String appLinkProgrammeAlias = getAppLinkProgrammeAlias(getIntent());

		// Has been launched by app link
		if (!TextUtils.isEmpty(appLinkProgrammeAlias))
		{
			setViewState(ViewState.LOADING);

			Call<List<Programme>> programmeCall = API.INSTANCE.getApi().getProgramme(appLinkProgrammeAlias);
			programmeCall.enqueue(new Callback<List<Programme>>()
			{
				@Override
				public void onResponse(Call<List<Programme>> call, Response<List<Programme>> response)
				{
					if (response != null && response.body() != null && !response.body().isEmpty())
					{
						setViewState(ViewState.LOADED);

						Programme programme = response.body().get(0);
						DetailsFragment detailsFragment = DetailsFragment.newInstance(programme);
						showNoHistoryFragment(detailsFragment);
					}
					else
					{
						onError();
					}
				}

				@Override
				public void onFailure(Call<List<Programme>> call, Throwable throwable)
				{
					onError();
				}

				private void onError()
				{
					Toast.makeText(MainActivity.this, "We weren't able to load this video. Please try again later.", Toast.LENGTH_LONG).show();
					finish();
				}
			});
		}
	}

	@Nullable private String getAppLinkProgrammeAlias(@Nullable Intent intent)
	{
		if (intent != null)
		{
			Uri data = intent.getData();

			if (data != null)
			{
				List<String> pathSegments = data.getPathSegments();

				if (pathSegments != null && !pathSegments.isEmpty() && pathSegments.size() > 1)
				{
					// 1st segment should be "radio" or "tv"
					// 2nd segment should be the video id
					return pathSegments.get(1);
				}
			}
		}

		return null;
	}

	private void setupNavigation()
	{
		ArrayList<AHBottomNavigationItem> items = new ArrayList<>();
		items.add(new AHBottomNavigationItem(R.string.title_home, R.drawable.ic_home, android.R.color.black));
		items.add(new AHBottomNavigationItem(R.string.title_live, R.drawable.ic_live, android.R.color.black));
		items.add(new AHBottomNavigationItem(R.string.title_channels, R.drawable.ic_channels, android.R.color.black));
		items.add(new AHBottomNavigationItem(R.string.title_events, R.drawable.ic_events, android.R.color.black));
		items.add(new AHBottomNavigationItem(R.string.title_search, R.drawable.ic_search, android.R.color.black));

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
	}

	@Override public int provideLoadingLayout()
	{
		return R.layout.main_loading;
	}

	@Override public int provideEmptyLayout()
	{
		return R.layout.main_empty;
	}

	@Override public int provideLoadedLayout()
	{
		return R.layout.main_loaded;
	}

	@Override public int provideErrorLayout()
	{
		return R.layout.main_error;
	}

	@Override public ViewState provideInitialViewState()
	{
		return ViewState.LOADED;
	}

	@Override public boolean onTabSelected(int position, boolean wasSelected)
	{
		switch (position)
		{
			case 0:
				showNoHistoryFragment(HomeFragment.newInstance());
				return true;
			case 1:
				showNoHistoryFragment(LiveFragment.newInstance());
				return true;
			case 2:
				showNoHistoryFragment(ChannelsFragment.newInstance());
				return true;
			case 3:
				showNoHistoryFragment(EventsFragment.newInstance());
				return true;
			case 4:
				showNoHistoryFragment(SearchFragment.newInstance());
				return true;
			default:
				throw new RuntimeException("Unknown tab selected");
		}
	}

	public void selectTab(@NonNull LushTab tab)
	{
		bottomNavigation.setCurrentItem(tab.getPosition());
	}

	public void showFragment(@NonNull Fragment fragment)
	{
		showFragment(fragment, true);
	}

	private void showNoHistoryFragment(@NonNull Fragment fragment)
	{
		showFragment(fragment, false);
	}

	private void showFragment(@NonNull Fragment fragment, boolean preserveHistory)
	{
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();

		if (preserveHistory)
		{
			transaction.add(container.getId(), fragment);
			transaction.addToBackStack(null);
		}
		else
		{
			// Tab selection
			transaction.replace(container.getId(), fragment);
		}

		transaction.commit();
	}

	@Override public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			if (getSupportFragmentManager().getBackStackEntryCount() == 0)
			{
				finish();
				return false;
			}

			getSupportFragmentManager().popBackStack();
			return false;
		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (resultCode == LushPlaybackActivity.RESULT_CODE)
		{
			Programme programme = (Programme)data.getSerializableExtra(LushPlaybackActivity.EXTRA_MEDIA_CONTENT);
			int startTime = data.getIntExtra(LushPlaybackActivity.EXTRA_START_TIME, 0);

			Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);

			if (fragment instanceof DetailsFragment)
			{
				DetailsFragment detailsFragment = (DetailsFragment)fragment;
				detailsFragment.seekTo(startTime);
			}
		}
	}
}