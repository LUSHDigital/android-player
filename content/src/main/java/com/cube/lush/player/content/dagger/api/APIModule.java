package com.cube.lush.player.content.dagger.api;

import com.cube.lush.player.api.LushAPI;
import com.cube.lush.player.api.util.HtmlStringAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import lombok.AllArgsConstructor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jamie Cruwys of 3 SIDED CUBE on 23/03/2017.
 */
@Module
public class APIModule
{
	private String baseUrl;

	public APIModule(String baseUrl)
	{
		this.baseUrl = baseUrl;
	}

	@Provides @Singleton Gson provideGson()
	{
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(String.class, new HtmlStringAdapter());
		gsonBuilder.setDateFormat("dd/MM/yyyy");

		return gsonBuilder.create();
	}

	@Provides @Singleton Retrofit provideRetrofit(Gson gson)
	{
		Retrofit.Builder builder = new Retrofit.Builder();
		builder.addConverterFactory(GsonConverterFactory.create(gson));
		builder.baseUrl(baseUrl);

		return builder.build();
	}

	@Provides @Singleton LushAPI provideLushAPI(Retrofit retrofit)
	{
		return retrofit.create(LushAPI.class);
	}
}