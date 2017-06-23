package com.cube.lush.player.content.repository;

import android.support.annotation.NonNull;

import com.lush.player.api.model.Channel;
import com.cube.lush.player.content.handler.ResponseHandler;

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
	private ChannelRepository() { }

	public static final ChannelRepository INSTANCE = new ChannelRepository();

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
}