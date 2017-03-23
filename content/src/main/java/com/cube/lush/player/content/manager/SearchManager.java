package com.cube.lush.player.content.manager;

import android.support.annotation.NonNull;

import com.cube.lush.player.api.LushAPI;
import com.cube.lush.player.content.dagger.DaggerComponents;
import com.cube.lush.player.content.handler.ResponseHandler;
import com.cube.lush.player.api.model.SearchResult;

import java.util.List;

import javax.inject.Inject;

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
	private static SearchManager instance;
	@Inject protected LushAPI api;

	public static SearchManager getInstance()
	{
		if (instance == null)
		{
			instance = new SearchManager();
		}

		return instance;
	}

	private SearchManager()
	{
		DaggerComponents.getInstance().getApi().inject(this);
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