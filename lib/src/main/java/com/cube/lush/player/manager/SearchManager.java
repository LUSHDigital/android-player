package com.cube.lush.player.manager;

import android.support.annotation.NonNull;

import com.cube.lush.player.api.LushAPI;
import com.cube.lush.player.handler.ResponseHandler;
import com.cube.lush.player.model.SearchResult;

import java.util.List;

import lombok.Getter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Jamie Cruwys
 * @project lush-player-android-client
 */
public class SearchManager
{
	@Getter private static SearchManager instance;
	private LushAPI api;

	public static void initialise(@NonNull LushAPI api)
	{
		instance = new SearchManager(api);
	}

	private SearchManager(@NonNull LushAPI api)
	{
		this.api = api;
	}

	public void search(@NonNull final String searchTerm, @NonNull final ResponseHandler<SearchResult> handler)
	{
		Call<List<SearchResult>> searchCall = api.search(searchTerm);

		searchCall.enqueue(new Callback<List<SearchResult>>()
		{
			@Override public void onResponse(Call<List<SearchResult>> call, Response<List<SearchResult>> searchResponse)
			{
				if (!searchResponse.isSuccessful())
				{
					handler.onFailure(null);
				}

				handler.onSuccess(searchResponse.body());
			}

			@Override public void onFailure(Call<List<SearchResult>> call, Throwable t)
			{
				handler.onFailure(t);
			}
		});
	}
}