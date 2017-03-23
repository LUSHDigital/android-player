package com.cube.lush.player.mobile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity
{
	private TextView mTextMessage;

	private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener()
	{

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

	};

	@Override protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_phone);

		mTextMessage = (TextView)findViewById(R.id.message);
		BottomNavigationView navigation = (BottomNavigationView)findViewById(R.id.navigation);
		navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
	}
}
