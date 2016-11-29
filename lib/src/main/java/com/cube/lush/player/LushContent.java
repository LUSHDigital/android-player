package com.cube.lush.player;

import com.cube.lush.player.api.LushAPI;
import com.cube.lush.player.api.HtmlStringAdapter;
import com.cube.lush.player.manager.MediaManager;
import com.cube.lush.player.manager.SearchManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LushContent
{
	private static final String BASE_URL = "http://admin.player.lush.com/";
	private static LushAPI api;

	/**
	 * This should be only called in MainApplication's onCreate method (once!)
	 */
	public static void initialise()
	{
		initialiseRetrofit();
		initialiseManagers();
	}

	private static void initialiseRetrofit()
	{
		Gson gson = new GsonBuilder()
			.registerTypeAdapter(String.class, new HtmlStringAdapter())
			.setDateFormat("dd/MM/yyyy")
			.create();
		Converter.Factory converterFactory = GsonConverterFactory.create(gson);

		Retrofit retrofit = new Retrofit.Builder()
			.baseUrl(BASE_URL)
			.addConverterFactory(converterFactory)
			.build();

		api = retrofit.create(LushAPI.class);
	}

	private static void initialiseManagers()
	{
		MediaManager.initialise(api);
		SearchManager.initialise(api);
	}
}
