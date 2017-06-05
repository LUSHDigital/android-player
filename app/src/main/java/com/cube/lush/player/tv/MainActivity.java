package com.cube.lush.player.tv;

import android.os.Bundle;

import com.cube.lush.player.tv.base.BaseTVActivity;
import com.cube.lush.player.R;

/**
 * Main Activity
 *
 * @author Jamie Cruwys
 */
public class MainActivity extends BaseTVActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
}
