package com.cube.lush.player.content.util;

import android.support.annotation.NonNull;

import com.lush.player.api.model.Programme;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Orders a set of programmes
 *
 * @author Jamie Cruwys
 */
public enum MediaSorter
{
	MOST_RECENT_FIRST,
	OLDEST_FIRST;

	/**
	 * Sorts items
	 *
	 * @param items
	 * @return programmes
	 */
	public List<Programme> sort(@NonNull List<Programme> items)
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
	 * @return programmes
	 */
	private List<Programme> byDateAscending(@NonNull List<Programme> items, final boolean ascending)
	{
		Collections.sort(items, new Comparator<Programme>()
		{
			@Override public int compare(Programme programme, Programme t1)
			{
				if (t1 == null || t1.getDate() == null)
				{
					return 1;
				}

				if (programme == null || programme.getDate() == null)
				{
					return -1;
				}

				int result = programme.getDate().compareTo(t1.getDate());

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