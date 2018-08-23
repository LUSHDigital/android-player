package com.cube.lush.player.content.repository;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.lush.player.api.model.ContentType;
import com.lush.player.api.model.Programme;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.lush.player.api.model.ContentType.RADIO;
import static com.lush.player.api.model.ContentType.TV;

/**
 * Base Programme repository to provide filtering by content type
 *
 * @author Jamie Cruwys
 */
abstract class BaseProgrammeRepository extends Repository<Programme>
{
	protected Set<Programme> videos = new HashSet<>();
	protected Set<Programme> radios = new HashSet<>();

	public BaseProgrammeRepository(@NonNull Context context)
	{
		super(context);
	}

	public Set<Programme> getVideos()
	{
		return videos;
	}

	public Set<Programme> getRadios()
	{
		return radios;
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
			if (programme == null || TextUtils.isEmpty(programme.getId()))
			{
				continue;
			}

			ContentType type = programme.getType();

			if (type == TV)
			{
				videos.add(programme);
			}
			else if (type == RADIO)
			{
				radios.add(programme);
			}
		}
	}
}