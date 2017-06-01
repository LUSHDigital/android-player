package com.cube.lush.player.mobile.events;

import android.support.annotation.NonNull;

import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Event Tab
 *
 * @author Jamie Cruwys
 */
@AllArgsConstructor
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
	@Getter private String displayName;

	/**
	 * Name of the tag that will be used to find the content
	 */
	@Getter private String tag;

	public static List<EventTab> listValues()
	{
		return Arrays.asList(EventTab.values());
	}

	public static List<EventTab> listValuesExcluding(@NonNull EventTab... tabsToExclude)
	{
		List<EventTab> tabs = listValues();

		for (EventTab tabToExclude : tabsToExclude)
		{
			tabs.remove(tabToExclude);
		}

		return tabs;
	}
}