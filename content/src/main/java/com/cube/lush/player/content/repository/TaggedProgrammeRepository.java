package com.cube.lush.player.content.repository;

import android.support.annotation.NonNull;

import com.cube.lush.player.api.model.Programme;
import com.cube.lush.player.content.handler.ResponseHandler;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Tagged Programme respository to provide a list of programmes for a tag
 *
 * @author Jamie Cruwys
 */
public class TaggedProgrammeRepository extends ProgrammeRepository
{
	public static final TaggedProgrammeRepository INSTANCE = new TaggedProgrammeRepository();

	private TaggedProgrammeRepository() { }

	@Getter @Setter @NonNull private String tag = "";

	@Override void getItemsFromNetwork(@NonNull final ResponseHandler<Programme> callback)
	{
		Call<List<Programme>> programmes = api.getProgrammesForTag(tag);

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