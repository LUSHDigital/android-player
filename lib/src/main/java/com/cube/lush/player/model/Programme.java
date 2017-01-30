package com.cube.lush.player.model;

import lombok.Data;

/**
 * Extension of {@link MediaContent} returned by {@link com.cube.lush.player.api.LushAPI#getProgramme(String)}, which returns additional information about a
 * specific piece of media.
 *
 * @author Jamie Cruwys
 * @project lush-player-android-client
 */
@Data
public class Programme extends MediaContent
{
	private String url;
	private String duration;
}
