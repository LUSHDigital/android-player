package com.cube.lush.player.mobile.home;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Jamie Cruwys of 3 SIDED CUBE on 12/04/2017.
 */
@AllArgsConstructor
public enum HomeTab
{
	@SuppressWarnings("HardCodedStringLiteral")
	ALL("All Episodes", null),

	@SuppressWarnings("HardCodedStringLiteral")
	SUMMIT("Lush Summit 2017", "summit"),

	@SuppressWarnings("HardCodedStringLiteral")
	SHOWCASE("Creative Showcase", "showcase 2016"),

	PERMACURE("Permacure", "permaculture");

	/**
	 * The name used for the tabs in the UI
	 */
	@Getter private String displayName;

	/**
	 * Name of the tag that will be used to find the content
	 */
	@Getter private String tag;
}