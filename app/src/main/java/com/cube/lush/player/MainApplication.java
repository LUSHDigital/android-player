package com.cube.lush.player;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

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
import com.squareup.picasso.Picasso;

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

		if (BuildConfig.DEBUG)
		{
			Picasso picasso = Picasso.with(context);
			picasso.setLoggingEnabled(true);
			picasso.setIndicatorsEnabled(true);
		}

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
					if (channel == null || TextUtils.isEmpty(channel.getTag()))
					{
						continue;
					}

					ChannelProgrammesRepository.getInstance(context).setChannelTag(channel.getTag());
					ChannelProgrammesRepository.getInstance(context).getItems(new ResponseHandler<Programme>()
					{
						@Override
						public void onSuccess(@NonNull List<Programme> items)
						{
							// Preload all images
							for (Programme programme : items)
							{
								if (programme == null || TextUtils.isEmpty(programme.getThumbnail()))
								{
									continue;
								}

								preloadImageUrl(programme.getThumbnail());
							}
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
					if (event == null || TextUtils.isEmpty(event.getTag()))
					{
						continue;
					}

					EventProgrammesRepository.getInstance(context).setEventTag(event.getTag());
					EventProgrammesRepository.getInstance(context).getItems(new ResponseHandler<Programme>()
					{
						@Override
						public void onSuccess(@NonNull List<Programme> items)
						{
							// Preload all images
							for (Programme programme : items)
							{
								if (programme == null || TextUtils.isEmpty(programme.getThumbnail()))
								{
									continue;
								}

								preloadImageUrl(programme.getThumbnail());
							}
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
				// Preload all images
				for (Programme programme : items)
				{
					if (programme == null || TextUtils.isEmpty(programme.getThumbnail()))
					{
						continue;
					}

					preloadImageUrl(programme.getThumbnail());
				}
			}

			@Override
			public void onFailure(@Nullable Throwable t)
			{
				// NO-OP, just trying to initialise the data set
			}
		});
	}

	private void preloadImageUrl(@Nullable String url)
	{
		if (!TextUtils.isEmpty(url))
		{
			Picasso.with(context)
					.load(url)
					.fetch();
		}
	}

	public static Context getContext()
	{
		return context;
	}
}