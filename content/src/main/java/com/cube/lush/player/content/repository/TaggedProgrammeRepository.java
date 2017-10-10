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
 * Tagged Programme respository to provide a list of programmes for a tag
 *
 * @author Jamie Cruwys
 */
public class TaggedProgrammeRepository extends BaseProgrammeRepository
{
	private static TaggedProgrammeRepository instance;
	@NonNull private String tag = "";

	public TaggedProgrammeRepository(@NonNull Context context)
	{
		super(context);
	}

	@NonNull
	public String getTag()
	{
		return tag;
	}

	public void setTag(@NonNull String tag)
	{
		this.tag = tag;
	}

	public static TaggedProgrammeRepository getInstance(@NonNull Context context)
	{
		if (instance == null)
		{
			instance = new TaggedProgrammeRepository(context);
		}

		return instance;
	}

	@Override void getItemsFromNetwork(@NonNull final ResponseHandler<Programme> callback)
	{
		tag = tag.replace("#", "");

		Call<List<Programme>> programmes = api.getProgrammesForTag(tag);

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
		return "TaggedProgramme";
	}
}