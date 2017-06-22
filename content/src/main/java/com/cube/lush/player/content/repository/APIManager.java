package com.cube.lush.player.content.repository;

import android.support.annotation.NonNull;

import com.cube.lush.player.api.LushAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * API Manager
 *
 * @author Jamie Cruwys
 */
public class APIManager
{
	private APIManager() { }

	public static final APIManager INSTANCE = new APIManager();
	private static final String DATE_FORMAT = "dd/MM/yyyy";
	private static final String BASE_URL = "http://admin.player.lush.com/lushtvapi/v2/";

	public LushAPI getAPI()
	{
		return getRetrofit(BASE_URL).create(LushAPI.class);
	}

	private Retrofit getRetrofit(@NonNull String baseUrl)
	{
		Retrofit.Builder builder = new Retrofit.Builder();
		builder.addConverterFactory(GsonConverterFactory.create(getGson()));
		builder.client(getOkHttpClient());
		builder.baseUrl(baseUrl);

		return builder.build();
	}

	private Gson getGson()
	{
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(DATE_FORMAT);

		return gsonBuilder.create();
	}

	private OkHttpClient getOkHttpClient()
	{
		OkHttpClient.Builder builder = new OkHttpClient.Builder();

		// Mock the playlist endpoint so it doesn't show live content
		// builder.addInterceptor(new MockLivePlaylistInterceptor(context));

		// Channel interceptors
		// builder.addInterceptor(new MockChannelsInterceptor(context));
		// builder.addInterceptor(new MockChannelProgrammesInterceptor(context));

		// Event interceptors
		// builder.addInterceptor(new MockEventsInterceptor(context));
		// builder.addInterceptor(new MockEventsProgrammesInterceptor(context));

		// Search interceptors
		// builder.addInterceptor(new MockSearchInterceptor(context));

		// Tag interceptors
		// builder.addInterceptor(new MockTagProgrammesInterceptor(context));

		return builder.build();
	}
}
