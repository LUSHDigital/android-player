package com.cube.lush.player.model;

import lombok.Data;

/**
 * @author Jamie Cruwys
 * @project lush-player-android-client
 */
@Data
public class RadioContent extends MediaContent
{
	private String file;
	private String duration;

	public RadioContent()
	{
		setType(ContentType.RADIO);
	}
}
