package com.cube.lush.player.content.repository;

import android.content.Context;
import android.support.annotation.NonNull;

import com.cube.lush.player.content.handler.ResponseHandler;
import com.google.gson.reflect.TypeToken;
import com.lush.player.api.model.LivePlaylist;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Live Playlist respository to provide a list of live playlists
 *
 * @author Jamie Cruwys
 */
public class LivePlaylistRepository extends Repository<LivePlaylist>
{
	private static LivePlaylistRepository instance;

	public LivePlaylistRepository(@NonNull Context context)
	{
		super(context);
	}

	public static LivePlaylistRepository getInstance(@NonNull Context context)
	{
		if (instance == null)
		{
			instance = new LivePlaylistRepository(context);
		}

		return instance;
	}

	@Override
	public int getCacheExpiryTime()
	{
		return 0;
	}

	@Override void getItemsFromNetwork(@NonNull final ResponseHandler<LivePlaylist> callback)
	{
		Call<List<LivePlaylist>> livePlaylist = api.getLivePlaylist("0");

		livePlaylist.enqueue(new Callback<List<LivePlaylist>>()
		{
			@Override public void onResponse(Call<List<LivePlaylist>> call, Response<List<LivePlaylist>> response)
			{
				if (response != null && response.isSuccessful() && response.body() != null)
				{
					if (callback != null)
					{
						callback.onSuccess(response.body());
					}
				}
			}

			@Override public void onFailure(Call<List<LivePlaylist>> call, Throwable t)
			{
				if (callback != null)
				{
					callback.onFailure(t);
				}
			}
		});
	}

	@Override
	protected TypeToken<List<LivePlaylist>> provideGsonTypeToken()
	{
		return new TypeToken<List<LivePlaylist>>(){};
	}

	@Override
	protected String providePreferenceName()
	{
		return "LivePlaylist";
	}
}