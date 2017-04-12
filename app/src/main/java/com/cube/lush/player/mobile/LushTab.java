package com.cube.lush.player.mobile;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum LushTab
{
	HOME(0),
	LIVE(1),
	CHANNELS(2),
	EVENTS(3),
	SEARCH(4);

	@Getter private int position;
}