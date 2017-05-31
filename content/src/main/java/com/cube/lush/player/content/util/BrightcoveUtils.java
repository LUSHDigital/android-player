package com.cube.lush.player.content.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.brightcove.player.model.Playlist;
import com.brightcove.player.model.Video;
import com.cube.lush.player.content.model.VideoInfo;
import com.google.gson.internal.bind.util.ISO8601Utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.util.List;
import java.util.Map;

/**
 * Brightcove Utility class to abstract away Brightcove's implementation
 *
 * @author Jamie Cruwys
 */
public class BrightcoveUtils
{
	public static final int SECOND = 1000;
	public static final int MINUTE = 60 * SECOND;
	public static final int HOUR = 60 * MINUTE;
	public static final int DAY = 24 * HOUR;

	@SuppressWarnings("HardCodedStringLiteral")
	private static final String PROPERTY_NAME = "name";

	@SuppressWarnings("HardCodedStringLiteral")
	private static final String PROPERTY_POSTER_SOURCES = "poster_sources";

	@SuppressWarnings("HardCodedStringLiteral")
	private static final String PROPERTY_SRC = "src";

	@SuppressWarnings("HardCodedStringLiteral")
	private static final String PROPERTY_THUMBNAIL = "thumbnail";

	public static String getVideoName(@NonNull Video video)
	{
		return video.getStringProperty(PROPERTY_NAME);
	}

	public static String getVideoThumbnail(@NonNull Video video)
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

	/**
	 * Determines which video, if any, in a playlist is currently "live"
	 *
	 * @param playlist
	 * @return
	 */
	@Nullable
	public VideoInfo findCurrentLiveVideo(Playlist playlist)
	{
		long nowUtc = System.currentTimeMillis();

		for (Video video: playlist.getVideos())
		{
			try
			{
				if (video.getProperties().get("customFields") instanceof Map)
				{
					Map customFields = (Map) video.getProperties().get("customFields");
					Object startTimeString = customFields.get("starttime");
					Object length = customFields.get("livebroadcastlength");
					if (startTimeString instanceof String && length instanceof String)
					{
						long startTimeUtc = ISO8601Utils.parse((String) startTimeString, new ParsePosition(0)).getTime();
						String[] lengthParts = ((String)length).split(":"); // length is in the format HH:MM:SS
						if (lengthParts.length != 3)
						{
							continue;
						}
						long endTimeUtc = startTimeUtc;
						endTimeUtc += Long.parseLong(lengthParts[2]) * SECOND;
						endTimeUtc += Long.parseLong(lengthParts[1]) * MINUTE;
						endTimeUtc += Long.parseLong(lengthParts[0]) * HOUR;

						if (nowUtc >= startTimeUtc && nowUtc < endTimeUtc)
						{
							VideoInfo videoInfo = new VideoInfo();
							videoInfo.setVideo(video);
							videoInfo.setStartTimeUtc(startTimeUtc);
							videoInfo.setEndTimeUtc(endTimeUtc);
							return videoInfo;
						}
					}
				}
			}
			catch (ParseException parseEx)
			{
				// Ignore parse exception
				Log.e("3SC", parseEx.getMessage(), parseEx);
			}
		}

		return null;
	}
}