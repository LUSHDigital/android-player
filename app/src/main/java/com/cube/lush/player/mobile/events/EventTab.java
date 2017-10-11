package com.cube.lush.player.mobile.events;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Event Tab
 *
 * @author Jamie Cruwys
 */
public enum EventTab
{
	@SuppressWarnings("HardCodedStringLiteral")
	ALL("All Events", null),

	@SuppressWarnings("HardCodedStringLiteral")
	SUMMIT("Lush Summit 2017", "summit"),

	@SuppressWarnings("HardCodedStringLiteral")
	SHOWCASE("Creative Showcase 2016", "showcase 2016");

	/**
	 * The name used for the tabs in the UI
	 */
	private String displayName;

	/**
	 * Name of the tag that will be used to find the content
	 */
	private String tag;

	EventTab(String displayName, String tag)
	{
		this.displayName = displayName;
		this.tag = tag;
	}

	public String getDisplayName()
	{
		return displayName;
	}

	public String getTag()
	{
		return tag;
	}

	public static List<EventTab> listValues()
	{
		return Arrays.asList(EventTab.values());
	}

	public static List<EventTab> listValuesExcluding(@NonNull EventTab... tabsToExclude)
	{
		List<EventTab> tabs = new ArrayList<>(listValues());

		if (tabs == null)
		{
			tabs = Collections.EMPTY_LIST;
		}

		if (!tabs.isEmpty())
		{
			for (EventTab tabToExclude : tabsToExclude)
			{
				tabs.remove(tabToExclude);
			}
		}

		return tabs;
	}
}