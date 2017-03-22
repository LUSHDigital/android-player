package com.cube.lush.player.content.util;

import android.support.annotation.NonNull;

import com.cube.lush.player.api.model.MediaContent;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Various pre-defined orderings of media.
 *
 * @author Jamie Cruwys
 * @project lush-player-android-client
 */
public enum MediaSorter
{
	MOST_RECENT_FIRST,
	OLDEST_FIRST;

	public <T extends MediaContent> List<T> sort(@NonNull List<T> items)
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
	private <T extends MediaContent> List<T> byDateAscending(@NonNull List<T> items, final boolean ascending)
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
