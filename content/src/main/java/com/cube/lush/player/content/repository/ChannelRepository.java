package com.cube.lush.player.content.repository;

import android.content.Context;
import android.support.annotation.NonNull;

import com.cube.lush.player.content.handler.ResponseHandler;
import com.google.gson.reflect.TypeToken;
import com.lush.player.api.model.Channel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Channels respository to provide a list of channels
 *
 * @author Jamie Cruwys
 */
public class ChannelRepository extends Repository<Channel>
{
	private static ChannelRepository instance;

	public ChannelRepository(@NonNull Context context)
	{
		super(context);
	}

	public static ChannelRepository getInstance(@NonNull Context context)
	{
		if (instance == null)
		{
			instance = new ChannelRepository(context);
		}

		return instance;
	}

	@Override void getItemsFromNetwork(@NonNull final ResponseHandler<Channel> callback)
	{
		Call<List<Channel>> channels = api.getChannels();

		channels.enqueue(new Callback<List<Channel>>()
		{
			@Override public void onResponse(Call<List<Channel>> call, Response<List<Channel>> response)
			{
				if (response != null && response.isSuccessful() && response.body() != null)
				{
					callback.onSuccess(response.body());
				}
				else
				{
					callback.onFailure(null);
				}
			}

			@Override public void onFailure(Call<List<Channel>> call, Throwable t)
			{
				callback.onFailure(t);
			}
		});
	}

	@Override
	protected TypeToken<List<Channel>> provideGsonTypeToken()
	{
		return new TypeToken<List<Channel>>(){};
	}

	@Override
	protected String providePreferenceName()
	{
		return "Channel";
	}
}