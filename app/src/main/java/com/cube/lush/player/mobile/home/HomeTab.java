package com.cube.lush.player.mobile.home;

import com.cube.lush.player.mobile.events.EventTab;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Jamie Cruwys.
 */
@AllArgsConstructor
public enum HomeTab
{
	@SuppressWarnings("HardCodedStringLiteral")
	ALL("All Episodes", null),

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

	public static List<HomeTab> listValues()
	{
		ArrayList<HomeTab> items = new ArrayList<>();

		for (HomeTab item : HomeTab.values())
		{
			items.add(item);
		}

		return items;
	}
}