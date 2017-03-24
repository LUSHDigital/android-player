package com.cube.lush.player.api.model;

import android.support.annotation.NonNull;
import android.text.format.DateUtils;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import static android.text.format.DateUtils.DAY_IN_MILLIS;

/**
 * Model object representing generic media content.
 *
 * @author Jamie Cruwys
 * @project lush-player-android-client
 */
@Data
public class MediaContent implements Serializable
{
	private String title;
	@SerializedName(value="id", alternate={"guid"})
	private String id;
	private String description;
	private Date date;
	private String thumbnail;
	private ContentType type;

	public String getRelativeDate()
	{
		long now = System.currentTimeMillis();
		long time = Math.min(date.getTime(), now); // Make sure we don't show content as being in the future
		CharSequence description = DateUtils.getRelativeTimeSpanString(time, now, DAY_IN_MILLIS);

		return description.toString();
	}
}