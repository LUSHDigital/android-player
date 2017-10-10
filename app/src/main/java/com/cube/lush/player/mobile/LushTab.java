package com.cube.lush.player.mobile;

/**
 * Lush Tab
 *
 * @author Jamie Cruwys
 */
public enum LushTab
{
	HOME(0),
	LIVE(1),
	CHANNELS(2),
	EVENTS(3),
	SEARCH(4);

	private int position;

	LushTab(int position)
	{
		this.position = position;
	}

	public int getPosition()
	{
		return position;
	}
}