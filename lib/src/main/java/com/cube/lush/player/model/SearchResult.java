package com.cube.lush.player.model;

import lombok.Data;

/**
 * @author Jamie Cruwys
 * @project lush-player-android-client
 */
@Data
public class SearchResult
{
	private String title;
	private String id;
	private ContentType type;
	private String videoThumbnail;
	private String radioThumbnail;
}