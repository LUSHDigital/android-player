package com.cube.lush.player.content.repository;

import android.content.Context;
import android.support.annotation.NonNull;

import com.cube.lush.player.content.handler.ResponseHandler;
import com.google.gson.reflect.TypeToken;
import com.lush.player.api.model.Programme;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Event Programme respository to provide a list of programmes for a event
 *
 * @author Jamie Cruwys
 */
public class EventProgrammesRepository extends BaseProgrammeRepository
{
	private static EventProgrammesRepository instance;

	public EventProgrammesRepository(@NonNull Context context)
	{
		super(context);
	}

	public static EventProgrammesRepository getInstance(@NonNull Context context)
	{
		if (instance == null)
		{
			instance = new EventProgrammesRepository(context);
		}

		return instance;
	}

	@Getter @Setter private String eventTag = "";

	@Override void getItemsFromNetwork(@NonNull final ResponseHandler<Programme> callback)
	{
		Call<List<Programme>> programmes = api.getEventProgrammes(eventTag);

		programmes.enqueue(new Callback<List<Programme>>()
		{
			@Override public void onResponse(Call<List<Programme>> call, Response<List<Programme>> response)
			{
				if (response != null && response.isSuccessful() && response.body() != null)
				{
					callback.onSuccess(response.body());
				}
			}

			@Override public void onFailure(Call<List<Programme>> call, Throwable t)
			{
				callback.onFailure(t);
			}
		});
	}

	@Override
	protected TypeToken<List<Programme>> provideGsonTypeToken()
	{
		return new TypeToken<List<Programme>>(){};
	}

	@Override
	protected String providePreferenceName()
	{
		return "EventProgrammes";
	}
}