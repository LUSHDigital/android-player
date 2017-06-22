package com.cube.lush.player.api.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Event API model
 *
 * @author Jamie Cruwys
 */
public class Event implements Serializable
{
	private String name;
	private String tag;
	private Date startDate;
	private Date endDate;

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
}