package com.cube.lush.player.content.dagger.api;

import android.content.Context;
import android.support.annotation.NonNull;

import com.cube.lush.player.api.LushAPI;
import com.cube.lush.player.api.util.HtmlStringAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Dagger API Module
 *
 * @author Jamie Cruwys
 */
@Module
public class APIModule
{
	@SuppressWarnings("HardCodedStringLiteral")
	private static final String DATE_FORMAT = "dd/MM/yyyy";

	private Context context;
	private String baseUrl;

	public APIModule(@NonNull Context context, @NonNull String baseUrl)
	{
		this.context = context;
		this.baseUrl = baseUrl;
	}

	@Provides @Singleton Gson provideGson()
	{
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(String.class, new HtmlStringAdapter());
		gsonBuilder.setDateFormat(DATE_FORMAT);

		return gsonBuilder.create();
	}

	@Provides @Singleton OkHttpClient provideOkHttp()
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

	@Provides @Singleton Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient)
	{
		Retrofit.Builder builder = new Retrofit.Builder();
		builder.addConverterFactory(GsonConverterFactory.create(gson));
		builder.client(okHttpClient);
		builder.baseUrl(baseUrl);

		return builder.build();
	}

	@Provides @Singleton LushAPI provideLushAPI(Retrofit retrofit)
	{
		return retrofit.create(LushAPI.class);
	}
}