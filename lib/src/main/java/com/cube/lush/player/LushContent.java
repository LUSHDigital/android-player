package com.cube.lush.player;

import com.cube.lush.player.api.LushAPI;
import com.cube.lush.player.api.HtmlStringAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Converter;
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
		Gson gson = new GsonBuilder().registerTypeAdapter(String.class, new HtmlStringAdapter()).create();
		Converter.Factory converterFactory = GsonConverterFactory.create(gson);

		Retrofit retrofit = new Retrofit.Builder()
			.baseUrl(BASE_URL)
			.addConverterFactory(converterFactory)
			.build();

		return retrofit.create(LushAPI.class);
	}
}
