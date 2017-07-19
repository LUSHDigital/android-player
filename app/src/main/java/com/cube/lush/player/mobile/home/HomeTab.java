package com.cube.lush.player.mobile.home;

import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Home Tab
 *
 * @author Jamie Cruwys
 */
@AllArgsConstructor
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
	@Getter private String displayName;

	/**
	 * Name of the tag that will be used to find the content
	 */
	@Getter private String tag;

	public static List<HomeTab> listValues()
	{
		return Arrays.asList(HomeTab.values());
	}
}