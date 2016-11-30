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

	public String getThumbnail()
	{
		if (type == ContentType.TV)
		{
			return videoThumbnail;
		}
		else if (type == ContentType.RADIO)
		{
			return radioThumbnail;
		}

		return null;
	}
}