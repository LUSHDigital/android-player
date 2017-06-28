package com.cube.lush.player;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.cube.lush.player.analytics.Track;
import com.cube.lush.player.content.handler.ResponseHandler;
import com.cube.lush.player.content.repository.ChannelProgrammesRepository;
import com.cube.lush.player.content.repository.ChannelRepository;
import com.cube.lush.player.content.repository.EventProgrammesRepository;
import com.cube.lush.player.content.repository.EventRepository;
import com.cube.lush.player.content.repository.LatestProgrammesRepository;
import com.lush.LushApplication;
import com.lush.player.api.API;
import com.lush.player.api.model.Channel;
import com.lush.player.api.model.Event;
import com.lush.player.api.model.Programme;

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

		preloadData();
	}

	private void preloadData()
	{
		final Context context = this;

		ChannelRepository.getInstance(context).getItems(new ResponseHandler<Channel>()
		{
			@Override
			public void onSuccess(@NonNull List<Channel> items)
			{
				for (Channel channel : items)
				{
					ChannelProgrammesRepository.getInstance(context).getItems(new ResponseHandler<Programme>()
					{
						@Override
						public void onSuccess(@NonNull List<Programme> items)
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

			@Override
			public void onFailure(@Nullable Throwable t)
			{
				// NO-OP, just trying to initialise the data set
			}
		});

		EventRepository.getInstance(context).getItems(new ResponseHandler<Event>()
		{
			@Override
			public void onSuccess(@NonNull List<Event> items)
			{
				for (Event event : items)
				{
					EventProgrammesRepository.getInstance(context).getItems(new ResponseHandler<Programme>()
					{
						@Override
						public void onSuccess(@NonNull List<Programme> items)
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

			@Override
			public void onFailure(@Nullable Throwable t)
			{
				// NO-OP, just trying to initialise the data set
			}
		});

		LatestProgrammesRepository.getInstance(this).getItems(new ResponseHandler<Programme>()
		{
			@Override
			public void onSuccess(@NonNull List<Programme> items)
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