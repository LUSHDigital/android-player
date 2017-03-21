package com.cube.lush.player.api.model;

import lombok.Data;

/**
 * Extension of {@link MediaContent} which contains information specific to radio shows.
 *
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
