package com.cube.lush.player.mobile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.cube.lush.player.mobile.nav.BottomNavigationItemSelectedListener;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
{
	@BindView(R2.id.navigation) BottomNavigationView navigation;
	@BindView(R2.id.container) BottomNavigationView container;

	@Override protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_phone);
		ButterKnife.bind(this);

		BottomNavigationItemSelectedListener navigationListener = new BottomNavigationItemSelectedListener(R.id.container, getSupportFragmentManager());
		navigation.setOnNavigationItemSelectedListener(navigationListener);
	}
}
