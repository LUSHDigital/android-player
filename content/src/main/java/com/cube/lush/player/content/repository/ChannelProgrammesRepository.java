package com.cube.lush.player.content.repository;

import android.content.Context;
import android.support.annotation.NonNull;

import com.cube.lush.player.content.handler.ResponseHandler;
import com.google.gson.reflect.TypeToken;
import com.lush.player.api.model.Programme;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Channels Programme respository to provide a list of programmes for a channel
 *
 * @author Jamie Cruwys
 */
public class ChannelProgrammesRepository extends BaseProgrammeRepository
{
	private static ChannelProgrammesRepository instance;

	public ChannelProgrammesRepository(@NonNull Context context)
	{
		super(context);
	}

	public static ChannelProgrammesRepository getInstance(@NonNull Context context)
	{
		if (instance == null)
		{
			instance = new ChannelProgrammesRepository(context);
		}

		return instance;
	}

	private String channelTag;

	public String getChannelTag()
	{
		return channelTag;
	}

	public void setChannelTag(String channelTag)
	{
		this.channelTag = channelTag;
	}

	@Override void getItemsFromNetwork(@NonNull final ResponseHandler<Programme> callback)
	{
		Call<List<Programme>> programmes = api.getChannelProgrammes(channelTag);

		programmes.enqueue(new Callback<List<Programme>>()
		{
			@Override public void onResponse(Call<List<Programme>> call, Response<List<Programme>> response)
			{
				if (response != null && response.isSuccessful() && response.body() != null)
				{
					if (callback != null)
					{
						callback.onSuccess(response.body());
					}
				}
			}

			@Override public void onFailure(Call<List<Programme>> call, Throwable t)
			{
				if (callback != null)
				{
					callback.onFailure(t);
				}
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
		return "ChannelProgrammes";
	}
}