package com.cube.lush.player.content.repository;

import android.content.Context;
import android.support.annotation.NonNull;

import com.lush.player.api.model.Programme;

/**
 * Programme Repository convenience class
 *
 * @author Jamie Cruwys
 */
public class ProgrammeRepository
{
	public static boolean isNew(@NonNull Context context, @NonNull Programme programme)
	{
		if (ChannelProgrammesRepository.getInstance(context).isNew(programme))
		{
			return true;
		}

		if (EventProgrammesRepository.getInstance(context).isNew(programme))
		{
			return true;
		}

		if (LatestProgrammesRepository.getInstance(context).isNew(programme))
		{
			return true;
		}

		if (SearchProgrammeRepository.getInstance(context).isNew(programme))
		{
			return true;
		}

		if (TaggedProgrammeRepository.getInstance(context).isNew(programme))
		{
			return true;
		}

		return false;
	}

	public static void watched(@NonNull Context context, @NonNull Programme programme)
	{
		ChannelProgrammesRepository.getInstance(context).watched(programme);
		EventProgrammesRepository.getInstance(context).watched(programme);
		LatestProgrammesRepository.getInstance(context).watched(programme);
		SearchProgrammeRepository.getInstance(context).watched(programme);
		TaggedProgrammeRepository.getInstance(context).watched(programme);
	}
}