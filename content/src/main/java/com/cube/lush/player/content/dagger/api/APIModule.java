package com.cube.lush.player.content.dagger.api;

import android.content.Context;
import android.support.annotation.NonNull;

import com.cube.lush.player.api.LushAPI;
import com.cube.lush.player.api.interceptors.MockPlaylistInterceptor;
import com.cube.lush.player.api.interceptors.MockProgrammeInterceptor;
import com.cube.lush.player.api.interceptors.MockRadiosInterceptor;
import com.cube.lush.player.api.interceptors.MockSearchInterceptor;
import com.cube.lush.player.api.interceptors.MockVideosInterceptor;
import com.cube.lush.player.api.interceptors.channels.MockCosmeticsChannelInterceptor;
import com.cube.lush.player.api.interceptors.channels.MockGorillaChannelInterceptor;
import com.cube.lush.player.api.interceptors.channels.MockKitchenChannelInterceptor;
import com.cube.lush.player.api.interceptors.channels.MockLushLifeChannelInterceptor;
import com.cube.lush.player.api.interceptors.channels.MockSoapboxChannelInterceptor;
import com.cube.lush.player.api.interceptors.channels.MockTimesChannelInterceptor;
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
 * Created by Jamie Cruwys of 3 SIDED CUBE on 23/03/2017.
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

		// Channel interceptors
		builder.addInterceptor(new MockCosmeticsChannelInterceptor(context));
		builder.addInterceptor(new MockGorillaChannelInterceptor(context));
		builder.addInterceptor(new MockKitchenChannelInterceptor(context));
		builder.addInterceptor(new MockLushLifeChannelInterceptor(context));
		builder.addInterceptor(new MockSoapboxChannelInterceptor(context));
		builder.addInterceptor(new MockTimesChannelInterceptor(context));

		// Other interceptors
		builder.addInterceptor(new MockPlaylistInterceptor(context));
		builder.addInterceptor(new MockProgrammeInterceptor(context));
		builder.addInterceptor(new MockRadiosInterceptor(context));
		builder.addInterceptor(new MockSearchInterceptor(context));
		builder.addInterceptor(new MockVideosInterceptor(context));

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