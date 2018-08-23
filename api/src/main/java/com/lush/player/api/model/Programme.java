package com.lush.player.api.model;

import android.text.format.DateUtils;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.text.format.DateUtils.DAY_IN_MILLIS;

/**
 * Programme API model
 *
 * @author Jamie Cruwys
 */
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
	private long duration;

	public String getRelativeDate()
	{
		long now = System.currentTimeMillis();
		CharSequence description = DateUtils.getRelativeTimeSpanString(getDateTime(), now, DAY_IN_MILLIS);

		return description.toString();
	}

	public long getDateTime()
	{
		if (date == null)
		{
			return 0;
		}

		long now = System.currentTimeMillis();

		// Make sure we don't show content as being in the future
		return Math.min(date.getTime(), now);
	}

	public String getWebLink()
	{
		return "http://player.lush.com/tv/" + alias;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public String getAlias()
	{
		return alias;
	}

	public void setAlias(String alias)
	{
		this.alias = alias;
	}

	public String getThumbnail()
	{
		return thumbnail;
	}

	public void setThumbnail(String thumbnail)
	{
		this.thumbnail = thumbnail;
	}

	public String getChannel()
	{
		return channel;
	}

	public void setChannel(String channel)
	{
		this.channel = channel;
	}

	public String getEvent()
	{
		return event;
	}

	public void setEvent(String event)
	{
		this.event = event;
	}

	public List<Tag> getTags()
	{
		return tags;
	}

	public void setTags(List<Tag> tags)
	{
		this.tags = tags;
	}

	public ContentType getType()
	{
		return type;
	}

	public void setType(ContentType type)
	{
		this.type = type;
	}

	public String getFile()
	{
		return file;
	}

	public void setFile(String file)
	{
		this.file = file;
	}

	public long getDuration()
	{
		return duration;
	}

	public void setDuration(long duration)
	{
		this.duration = duration;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Programme programme = (Programme) o;

		return id != null ? id.equals(programme.id) : programme.id == null;

	}

	@Override
	public int hashCode()
	{
		return id != null ? id.hashCode() : 0;
	}
}