package com.cube.lush.player.content.repository;

import com.lush.player.api.API;
import com.lush.player.api.LushAPI;

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