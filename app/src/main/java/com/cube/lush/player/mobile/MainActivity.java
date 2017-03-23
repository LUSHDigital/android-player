package com.cube.lush.player.mobile;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.widget.FrameLayout;

import com.cube.lush.player.common.BaseActivity;
import com.cube.lush.player.mobile.nav.BottomNavigationItemSelectedListener;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.cube.lush.player.R;

public class MainActivity extends BaseActivity
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
}