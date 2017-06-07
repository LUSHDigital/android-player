package com.cube.lush.player;

import com.cube.lush.player.analytics.Track;
import com.lush.LushApplication;

/**
 * Main Application
 *
 * @author Jamie Cruwys
 */
public class MainApplication extends LushApplication
{
	@Override public void onCreate()
	{
		super.onCreate();

		Track.initialise(this);
	}
}