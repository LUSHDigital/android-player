package com.cube.lush.player.content.repository;

import com.cube.lush.player.api.API;
import com.cube.lush.player.api.LushAPI;

/**
 * API Manager
 *
 * @author Jamie Cruwys
 */
public class APIManager
{
	private APIManager() { }

	public static final APIManager INSTANCE = new APIManager();

	public LushAPI getAPI()
	{
		return API.INSTANCE.getApi();
	}
}