package com.cube.lush.player.content.util;

import android.support.annotation.NonNull;

import com.brightcove.player.model.Video;

import java.util.List;
import java.util.Map;

/**
 * Created by Jamie Cruwys of 3 SIDED CUBE on 29/03/2017.
 */
public class VideoUtils
{
	@SuppressWarnings("HardCodedStringLiteral")
	private static final String PROPERTY_NAME = "name";

	@SuppressWarnings("HardCodedStringLiteral")
	private static final String PROPERTY_POSTER_SOURCES = "poster_sources";

	@SuppressWarnings("HardCodedStringLiteral")
	private static final String PROPERTY_SRC = "src";

	@SuppressWarnings("HardCodedStringLiteral")
	private static final String PROPERTY_THUMBNAIL = "thumbnail";

	public static String getName(@NonNull Video video)
	{
		return video.getStringProperty(PROPERTY_NAME);
	}

	public static String getImage(@NonNull Video video)
	{
		Object posterSourcesObject = video.getProperties().get(PROPERTY_POSTER_SOURCES);

		// Painfully find an appropriate image to display as the background
		if (posterSourcesObject instanceof List)
		{
			List posterSources = (List)posterSourcesObject;
			if (!posterSources.isEmpty() && posterSources.get(0) instanceof Map)
			{
				Map firstPosterSource = (Map) posterSources.get(0);
				if (firstPosterSource.get(PROPERTY_SRC) instanceof String)
				{
					return (String)firstPosterSource.get(PROPERTY_SRC);
				}
			}
		}

		return video.getStringProperty(PROPERTY_THUMBNAIL);
	}
}