package com.lush.player.api.model;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

/**
 * Event API model
 *
 * @author Jamie Cruwys
 */
public class Event implements Serializable
{
	protected String name;
	protected String tag;
	protected Date startDate;
	protected Date endDate;

	public Event() { }

	public Event(@NonNull String name)
	{
		this.name = name;
		this.tag = name;

		long now = System.currentTimeMillis();
		long day = 1000 * 60 * 60 * 24;
		long dayLater = now + day;

		startDate = new Date(now);
		endDate = new Date(dayLater);
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getTag()
	{
		return tag;
	}

	public void setTag(String tag)
	{
		this.tag = tag;
	}

	public Date getStartDate()
	{
		return startDate;
	}

	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}

	public Date getEndDate()
	{
		return endDate;
	}

	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Event event = (Event) o;

		return tag != null ? tag.equals(event.tag) : event.tag == null;

	}

	@Override
	public int hashCode()
	{
		return tag != null ? tag.hashCode() : 0;
	}
}