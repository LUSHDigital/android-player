package com.cube.lush.player.util;

import android.support.annotation.NonNull;

import com.cube.lush.player.model.MediaContent;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Jamie Cruwys
 * @project lush-player-android-client
 */
public enum MediaSorter
{
	MOST_RECENT_FIRST,
	OLDEST_FIRST;

	public List<MediaContent> sort(@NonNull List<MediaContent> items)
	{
		if (this == MOST_RECENT_FIRST)
		{
			return byDateAscending(items, false);
		}
		else if (this == OLDEST_FIRST)
		{
			return byDateAscending(items, true);
		}

		return items;
	}

	/**
	 * Sorts the media content by date either ascending or descending
	 *
	 * @param items
	 * @param ascending true for ascending, false for descending
	 * @return
	 */
	private List<MediaContent> byDateAscending(@NonNull List<MediaContent> items, final boolean ascending)
	{
		Collections.sort(items, new Comparator<MediaContent>()
		{
			@Override public int compare(MediaContent mediaContent, MediaContent t1)
			{
				int result = mediaContent.getDate().compareTo(t1.getDate());

				// Currently ordered with the oldest date first e.g. 1st July, 1st August, 1st September

				// Flip the sorting order from ascending to descending
				if (!ascending)
				{
					// Now ordered with the most recent date first e.g. 1st September, 1st August, 1st July
					result *= -1;
				}

				return result;
			}
		});

		return items;
	}
}