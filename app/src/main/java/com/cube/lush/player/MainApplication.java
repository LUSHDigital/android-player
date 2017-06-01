package com.cube.lush.player;

import com.cube.lush.player.content.dagger.DaggerComponents;
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

		// Seed the dagger components class with a context
		DaggerComponents.getInstance(this);
	}
}