package com.cube.lush.player.content.repository;

import com.cube.lush.player.api.model.ContentType;
import com.cube.lush.player.api.model.Programme;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Getter;

/**
 * Created by Jamie Cruwys of 3 SIDED CUBE on 26/05/2017.
 */
public class ProgrammeRepository extends Repository<Programme>
{
	public static final ProgrammeRepository INSTANCE = new ProgrammeRepository();

	@Getter protected Set<Programme> videos = new HashSet<>();
	@Getter protected Set<Programme> radios = new HashSet<>();

	public void refresh()
	{

	}

	@Override public void update(List<Programme> updatedItems)
	{
		super.update(updatedItems);

		videos.clear();
		radios.clear();

		for (Programme item : items)
		{
			ContentType type = item.getType();

			if (type == ContentType.TV)
			{
				videos.add(item);
			}
			else if (type == ContentType.RADIO)
			{
				radios.add(item);
			}
		}
	}
}