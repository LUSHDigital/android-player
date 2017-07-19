package com.cube.lush.player.content.repository;

import com.google.gson.Gson;

/**
 * <Class Description>
 *
 * @author Jamie Cruwys
 */
public class GsonSingleton
{
	private static Gson gson;

	public static Gson getInstance()
	{
		if (gson == null)
		{
			gson = new Gson();
		}

		return gson;
	}
}