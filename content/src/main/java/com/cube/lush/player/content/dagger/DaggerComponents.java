package com.cube.lush.player.content.dagger;

import android.content.Context;
import android.support.annotation.Nullable;

import com.cube.lush.player.content.dagger.api.APIComponent;
import com.cube.lush.player.content.dagger.api.APIModule;
import com.cube.lush.player.content.dagger.api.DaggerAPIComponent;

import lombok.Getter;

/**
 * Dagger components
 *
 * @author Jamie Cruwys
 */
public class DaggerComponents
{
	@SuppressWarnings("HardCodedStringLiteral")
	private static final String BASE_URL = "http://admin.player.lush.com/lushtvapi/v2/";

	private static DaggerComponents instance;
	private static Context context;
	@Getter protected APIComponent api;

	public static DaggerComponents getInstance()
	{
		return getInstance(null);
	}

	public static DaggerComponents getInstance(@Nullable Context currentContext)
	{
		if (currentContext != null)
		{
			context = currentContext;
		}

		if (instance == null)
		{
			instance = new DaggerComponents();
		}

		return instance;
	}

	private DaggerComponents()
	{
		api = DaggerAPIComponent.builder()
			.aPIModule(new APIModule(context, BASE_URL))
			.build();
	}
}