package com.cube.lush.player.api.model;

import lombok.Data;

/**
 * Extension of {@link MediaContent} which contains information specific to video items.
 *
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
