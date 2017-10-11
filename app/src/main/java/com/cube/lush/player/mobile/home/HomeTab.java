package com.cube.lush.player.mobile.home;

import java.util.Arrays;
import java.util.List;

/**
 * Home Tab
 *
 * @author Jamie Cruwys
 */
public enum HomeTab
{
	@SuppressWarnings("HardCodedStringLiteral")
	ALL("All Episodes", null),

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

	HomeTab(String displayName, String tag)
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

	public static List<HomeTab> listValues()
	{
		return Arrays.asList(HomeTab.values());
	}
}