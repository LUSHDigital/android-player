package com.cube.lush.player.content.repository;

import android.support.annotation.NonNull;

import com.lush.player.api.model.Programme;
import com.cube.lush.player.content.handler.ResponseHandler;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
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
	public static final ChannelProgrammesRepository INSTANCE = new ChannelProgrammesRepository();

	private ChannelProgrammesRepository() { }

	@Getter @Setter private String channelTag;

	@Override void getItemsFromNetwork(@NonNull final ResponseHandler<Programme> callback)
	{
		Call<List<Programme>> programmes = api.getChannelProgrammes(channelTag);

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
}