package com.cube.lush.player.mobile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.FrameLayout;

import com.cube.lush.player.mobile.base.BaseMobileActivity;
import com.cube.lush.player.mobile.nav.BottomNavigationItemSelectedListener;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.cube.lush.player.R;

public class MainActivity extends BaseMobileActivity
{
    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    @BindView(R.id.container)
    FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_phone);
        ButterKnife.bind(this);

        BottomNavigationItemSelectedListener navigationListener = new BottomNavigationItemSelectedListener(container, getSupportFragmentManager());
        navigation.setOnNavigationItemSelectedListener(navigationListener);

        navigationListener.showDefaultItem();
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