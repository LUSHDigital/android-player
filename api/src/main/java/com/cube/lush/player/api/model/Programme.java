package com.cube.lush.player.api.model;

import android.text.format.DateUtils;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

import static android.text.format.DateUtils.DAY_IN_MILLIS;

/**
 * Created by Jamie Cruwys of 3 SIDED CUBE on 31/05/2017.
 */
@Data
public class Programme implements Serializable
{
	private String title;
	@SerializedName(value="id", alternate={"guid"})
	private String id;
	private String description;
	private Date date;
	private String alias;
	private String thumbnail;
	private String channel;
	private String event;
	private List<Tag> tags = new ArrayList<>();
	private ContentType type;
	private String file;
	private String duration;

	public String getRelativeDate()
	{
		long now = System.currentTimeMillis();
		long time = Math.min(date.getTime(), now); // Make sure we don't show content as being in the future
		CharSequence description = DateUtils.getRelativeTimeSpanString(time, now, DAY_IN_MILLIS);

		return description.toString();
	}

	public String getWebLink()
	{
		return "http://player.lush.com/tv/" + alias;
	}
}