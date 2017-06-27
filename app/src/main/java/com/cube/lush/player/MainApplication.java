package com.cube.lush.player;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.cube.lush.player.analytics.Track;
import com.cube.lush.player.content.handler.ResponseHandler;
import com.cube.lush.player.content.repository.EventRepository;
import com.lush.player.api.API;
import com.lush.LushApplication;
import com.lush.player.api.model.Event;

import java.util.List;

/**
 * Main Application
 *
 * @author Jamie Cruwys
 */
public class MainApplication extends LushApplication
{
	@Override public void onCreate()
	{
		super.onCreate();

		API.INSTANCE.initialise(this);
		Track.initialise(this);

		EventRepository.INSTANCE.getItems(new ResponseHandler<Event>()
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
}