package com.cube.lush.player;

import android.app.Application;

import com.cube.lush.player.api.LushAPI;

/**
 * @author Jamie Cruwys
 * @project lush-player-android-client
 */
public class MainApplication extends Application
{
	private static LushAPI api;

	@Override public void onCreate()
	{
		super.onCreate();

		api = LushContent.initialise();
	}

	public static LushAPI getAPI()
	{
		return api;
	}
}
