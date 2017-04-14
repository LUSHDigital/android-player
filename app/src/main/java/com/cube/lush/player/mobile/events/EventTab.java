package com.cube.lush.player.mobile.events;

import com.cube.lush.player.content.model.CategoryContentType;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Jamie Cruwys.
 */
@AllArgsConstructor
public enum EventTab
{
	@SuppressWarnings("HardCodedStringLiteral")
	ALL("All Events", null),

	@SuppressWarnings("HardCodedStringLiteral")
	SUMMIT("Lush Summit 2017", "summit"),

	@SuppressWarnings("HardCodedStringLiteral")
	SHOWCASE("Creative Showcase", "showcase 2016");

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
		ArrayList<EventTab> items = new ArrayList<>();

		for (EventTab item : EventTab.values())
		{
			items.add(item);
		}

		return items;
	}
}