package com.cube.lush.player.model;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

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

	@SerializedName("video_thumbnail")
	private String videoThumbnail;

	@SerializedName("radio_thumbnail")
	private String radioThumbnail;

	public String getThumbnail()
	{
		if (type == null)
		{
			if (!TextUtils.isEmpty(videoThumbnail))
			{
				return videoThumbnail;
			}

			if (!TextUtils.isEmpty(radioThumbnail))
			{
				return radioThumbnail;
			}
		}
		else
		{
			if (type == ContentType.TV)
			{
				return videoThumbnail;
			}
			else if (type == ContentType.RADIO)
			{
				return radioThumbnail;
			}
		}

		return null;
	}
}