package com.cube.lush.player.content.repository;

import android.content.Context;
import android.support.annotation.NonNull;

import com.cube.lush.player.content.handler.ResponseHandler;
import com.google.gson.reflect.TypeToken;
import com.lush.player.api.model.Event;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Event respository to provide a list of all events
 *
 * @author Jamie Cruwys
 */
public class EventRepository extends Repository<Event>
{
	public static final Event ALL_EVENTS = new Event("All Events");

	private static EventRepository instance;

	public EventRepository(@NonNull Context context)
	{
		super(context);
	}

	public static EventRepository getInstance(@NonNull Context context)
	{
		if (instance == null)
		{
			instance = new EventRepository(context);
		}

		return instance;
	}

	@Override void getItemsFromNetwork(@NonNull final ResponseHandler<Event> callback)
	{
		Call<List<Event>> events = api.getEvents();

		events.enqueue(new Callback<List<Event>>()
		{
			@Override public void onResponse(Call<List<Event>> call, Response<List<Event>> response)
			{
				if (response != null && response.isSuccessful() && response.body() != null)
				{
					if (callback != null)
					{
						callback.onSuccess(response.body());
					}
				}
			}

			@Override public void onFailure(Call<List<Event>> call, Throwable t)
			{
				if (callback != null)
				{
					callback.onFailure(t);
				}
			}
		});
	}

	@Override
	protected TypeToken<List<Event>> provideGsonTypeToken()
	{
		return new TypeToken<List<Event>>(){};
	}

	@Override
	protected String providePreferenceName()
	{
		return "Event";
	}

	public List<Event> getEventTabs()
	{
		ArrayList<Event> events = new ArrayList<Event>();
		events.add(ALL_EVENTS);
		events.addAll(getItemsSynchronously());

		return events;
	}
}