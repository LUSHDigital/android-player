package com.cube.lush.player;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.cube.lush.player.analytics.Track;
import com.cube.lush.player.content.handler.ResponseHandler;
import com.cube.lush.player.content.repository.EventRepository;
import com.lush.LushApplication;
import com.lush.player.api.API;
import com.lush.player.api.model.Event;

import java.util.List;

/**
 * Main Application
 *
 * @author Jamie Cruwys
 */
public class MainApplication extends LushApplication
{
	private static Context context;

	@Override public void onCreate()
	{
		super.onCreate();
		context = this;

		API.INSTANCE.initialise(this);
		Track.initialise(this);

		EventRepository.getInstance(this).getItems(new ResponseHandler<Event>()
		{
			@Override
			public void onSuccess(@NonNull List<Event> items)
			{
				// NO-OP, just trying to initialise the data set
			}

			@Override
			public void onFailure(@Nullable Throwable t)
			{
				// NO-OP, just trying to initialise the data set
			}
		});
	}

	public static Context getContext()
	{
		return context;
	}
}