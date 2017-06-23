package com.cube.lush.player.content.repository;

import android.support.annotation.NonNull;

import com.lush.player.api.model.Programme;

/**
 * Programme Repository convenience class
 *
 * @author Jamie Cruwys
 */
public class ProgrammeRepository
{
	public static boolean isNew(@NonNull Programme programme)
	{
		if (ChannelProgrammesRepository.INSTANCE.isNew(programme))
		{
			return true;
		}

		if (EventProgrammesRepository.INSTANCE.isNew(programme))
		{
			return true;
		}

		if (LatestProgrammesRepository.INSTANCE.isNew(programme))
		{
			return true;
		}

		if (SearchProgrammeRepository.INSTANCE.isNew(programme))
		{
			return true;
		}

		if (TaggedProgrammeRepository.INSTANCE.isNew(programme))
		{
			return true;
		}

		return false;
	}

	public static void watched(@NonNull Programme programme)
	{
		ChannelProgrammesRepository.INSTANCE.watched(programme);
		EventProgrammesRepository.INSTANCE.watched(programme);
		LatestProgrammesRepository.INSTANCE.watched(programme);
		SearchProgrammeRepository.INSTANCE.watched(programme);
		TaggedProgrammeRepository.INSTANCE.watched(programme);
	}
}