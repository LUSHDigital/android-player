package com.cube.lush.player.content.repository;

import android.support.annotation.NonNull;
import android.util.Log;

import com.cube.lush.player.api.model.ContentType;
import com.cube.lush.player.api.model.Programme;
import com.cube.lush.player.api.model.RadioContent;
import com.cube.lush.player.content.handler.ResponseHandler;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import retrofit2.Call;

/**
 * Created by Jamie Cruwys of 3 SIDED CUBE on 26/05/2017.
 */
public class ProgrammeRepository extends Repository<Programme>
{
	public static final ProgrammeRepository INSTANCE = new ProgrammeRepository();

	@Getter protected Set<Programme> videos = new HashSet<>();
	@Getter protected Set<Programme> radios = new HashSet<>();

	@NonNull @Override Set<Programme> getItemsFromNetwork(@NonNull final ResponseHandler<Programme> callback)
	{
		Call<List<RadioContent>> radios = api.getRadios();
		return null;
	}

	@Override protected void updateItems(@NonNull List<Programme> latestItems)
	{
		super.updateItems(latestItems);

		if (latestItems.isEmpty())
		{
			return;
		}

		for (Programme programme : latestItems)
		{
			ContentType type = programme.getType();

			switch (type)
			{
				case TV:
					videos.add(programme);
					break;
				case RADIO:
					radios.add(programme);
					break;
				default:
					Log.e(TAG, "Unsupported " + ContentType.class.getSimpleName() + ", " + type.toString());
			}
		}
	}
}