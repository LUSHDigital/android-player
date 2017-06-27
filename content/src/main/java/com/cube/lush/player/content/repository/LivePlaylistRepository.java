package com.cube.lush.player.content.repository;

import android.support.annotation.NonNull;

import com.lush.player.api.model.LivePlaylist;
import com.cube.lush.player.content.handler.ResponseHandler;

import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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
	private LivePlaylistRepository() { }

	public static final LivePlaylistRepository INSTANCE = new LivePlaylistRepository();

	@Override void getItemsFromNetwork(@NonNull final ResponseHandler<LivePlaylist> callback)
	{
		int utcOffsetMillis = TimeZone.getDefault().getOffset(new Date().getTime());
		String offsetString = String.format("%d minutes", utcOffsetMillis / 1000 / 60);

		offsetString = "0";

		Call<List<LivePlaylist>> livePlaylist = api.getLivePlaylist(String.valueOf(offsetString));

		livePlaylist.enqueue(new Callback<List<LivePlaylist>>()
		{
			@Override public void onResponse(Call<List<LivePlaylist>> call, Response<List<LivePlaylist>> response)
			{
				if (response != null && response.isSuccessful() && response.body() != null)
				{
					callback.onSuccess(response.body());
				}
			}

			@Override public void onFailure(Call<List<LivePlaylist>> call, Throwable t)
			{
				callback.onFailure(t);
			}
		});
	}
}