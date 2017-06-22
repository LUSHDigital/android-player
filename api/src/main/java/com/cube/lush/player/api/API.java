package com.cube.lush.player.api;

import android.content.Context;
import android.support.annotation.NonNull;

import com.lush.player.api.interceptor.MockChannelProgrammesInterceptor;
import com.lush.player.api.interceptor.MockChannelsInterceptor;
import com.lush.player.api.interceptor.MockEventsInterceptor;
import com.lush.player.api.interceptor.MockEventsProgrammesInterceptor;
import com.lush.player.api.interceptor.MockLivePlaylistInterceptor;
import com.lush.player.api.interceptor.MockSearchInterceptor;
import com.lush.player.api.interceptor.MockTagProgrammesInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Player API
 *
 * @author Jamie Cruwys
 */
public class API
{
	public static final API INSTANCE = new API();
	private static final String BASE_URL = "http://admin.player.lush.com/lushtvapi/v2/";
	private static final String DATE_FORMAT = "dd/MM/yyyy";

	private LushAPI api;
	private boolean isMocked = false;

	public LushAPI getApi()
	{
		return api;
	}

	public boolean isMocked()
	{
		return isMocked;
	}

	public void setMocked(boolean mocked)
	{
		isMocked = mocked;
	}

	/**
	 * Must be called in your MainApplication class
	 * @param context
	 */
	public void initialise(@NonNull Context context)
	{
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setDateFormat(DATE_FORMAT);
		Gson gson = gsonBuilder.create();

		Retrofit.Builder builder = new Retrofit.Builder();
		builder.addConverterFactory(GsonConverterFactory.create(gson));
		builder.client(getHttpClient(context));
		builder.baseUrl(BASE_URL);

		Retrofit retrofit = builder.build();

		api = retrofit.create(LushAPI.class);
	}

	private OkHttpClient getHttpClient(@NonNull Context context)
	{
		OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

		// Mock the playlist endpoint so it doesn't show live content
		okHttpBuilder.addInterceptor(new MockLivePlaylistInterceptor(context));

		// Channel interceptors
		okHttpBuilder.addInterceptor(new MockChannelsInterceptor(context));
		okHttpBuilder.addInterceptor(new MockChannelProgrammesInterceptor(context));

		// Event interceptors
		okHttpBuilder.addInterceptor(new MockEventsInterceptor(context));
		okHttpBuilder.addInterceptor(new MockEventsProgrammesInterceptor(context));

		// Search interceptors
		okHttpBuilder.addInterceptor(new MockSearchInterceptor(context));

		// Tag interceptors
		okHttpBuilder.addInterceptor(new MockTagProgrammesInterceptor(context));

		return okHttpBuilder.build();
	}
}