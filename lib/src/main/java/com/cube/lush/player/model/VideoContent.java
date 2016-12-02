package com.cube.lush.player.model;

import lombok.Data;

/**
 * @author Jamie Cruwys
 * @project lush-player-android-client
 */
@Data
public class VideoContent extends MediaContent
{
	// Add any extra fields if the API response changes
	public VideoContent()
	{
		setType(ContentType.TV);
	}
}
