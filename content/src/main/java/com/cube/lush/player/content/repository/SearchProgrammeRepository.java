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
 * Search Programme respository to provide a list of programmes for a search term
 *
 * @author Jamie Cruwys
 */
public class SearchProgrammeRepository extends BaseProgrammeRepository
{
	private static SearchProgrammeRepository instance;
	@NonNull private String searchTerm = "";

	public SearchProgrammeRepository(@NonNull Context context)
	{
		super(context);
	}

	@NonNull
	public String getSearchTerm()
	{
		return searchTerm;
	}

	public void setSearchTerm(@NonNull String searchTerm)
	{
		this.searchTerm = searchTerm;
	}

	public static SearchProgrammeRepository getInstance(@NonNull Context context)
	{
		if (instance == null)
		{
			instance = new SearchProgrammeRepository(context);
		}

		return instance;
	}

	@Override void getItemsFromNetwork(@NonNull final ResponseHandler<Programme> callback)
	{
		String requestSearchTerm = searchTerm;
		requestSearchTerm = requestSearchTerm.replace(" ", "+");
		
		Call<List<Programme>> programmes = api.search(requestSearchTerm);

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
		return "SearchProgramme";
	}
}