package com.cube.lush.player;

import android.content.Context;

import com.cube.lush.player.api.HtmlStringAdapter;
import com.cube.lush.player.api.LushAPI;
import com.cube.lush.player.manager.MediaManager;
import com.cube.lush.player.manager.SearchManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Manages access to the Lush API and media manager.
 */
public class LushContent
{
	private static final String API_VERSION = "1";
	private static final String BASE_URL = "http://admin.player.lush.com/lushtvapi/v" + API_VERSION + "/views/";
	private static LushAPI api;

	/**
	 * This should be only called in MainApplication's onCreate method (once!)
	 */
	public static void initialise(Context context)
	{
		initialiseRetrofit();
		initialiseManagers(context);
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

	private static void initialiseManagers(Context context)
	{
		MediaManager.initialise(context, api);
		SearchManager.initialise(api);
	}
}
