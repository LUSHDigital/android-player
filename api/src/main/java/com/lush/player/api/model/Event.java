package com.lush.player.api.model;

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
		// Generated equals method for object comparison
		if (this == o)
		{
			return true;
		}

		if (o == null || getClass() != o.getClass())
		{
			return false;
		}

		Event event = (Event) o;

		if (name != null ? !name.equals(event.name) : event.name != null)
		{
			return false;
		}

		if (tag != null ? !tag.equals(event.tag) : event.tag != null)
		{
			return false;
		}

		if (startDate != null ? !startDate.equals(event.startDate) : event.startDate != null)
		{
			return false;
		}

		return endDate != null ? endDate.equals(event.endDate) : event.endDate == null;
	}

	@Override
	public int hashCode()
	{
		// Generated hashcode method for object comparison

		int result = name != null ? name.hashCode() : 0;
		result = 31 * result + (tag != null ? tag.hashCode() : 0);
		result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
		result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
		return result;
	}
}