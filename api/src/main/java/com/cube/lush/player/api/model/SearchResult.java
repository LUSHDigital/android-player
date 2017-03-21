package com.cube.lush.player.api.model;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Data;

/**
 * Extension of {@link MediaContent} which contains extra information returned by the {@link com.cube.lush.player.api.LushAPI#search(String)} endpoint.
 *
 * @author Jamie Cruwys
 * @project lush-player-android-client
 */
@Data
public class SearchResult extends MediaContent implements Serializable
{
	@SerializedName("video_thumbnail")
	private String videoThumbnail;

	@SerializedName("radio_thumbnail")
	private String radioThumbnail;

	@Override
	public String getThumbnail()
	{
		if (getType() == null)
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
			if (getType() == ContentType.TV)
			{
				return videoThumbnail;
			}
			else if (getType() == ContentType.RADIO)
			{
				return radioThumbnail;
			}
		}

		return null;
	}
}
