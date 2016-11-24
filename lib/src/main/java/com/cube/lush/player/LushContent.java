package com.cube.lush.player;

import com.cube.lush.player.api.LushAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LushContent
{
	private static final String BASE_URL = "http://admin.player.lush.com/";

	/**
	 * This should be only called in MainApplication's onCreate method (once!)
	 */
	public static LushAPI initialise()
	{
		Retrofit retrofit = new Retrofit.Builder()
			.baseUrl(BASE_URL)
			.addConverterFactory(GsonConverterFactory.create())
			.build();

		return retrofit.create(LushAPI.class);
	}
}