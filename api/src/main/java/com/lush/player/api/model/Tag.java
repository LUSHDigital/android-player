package com.cube.lush.player.api.model;

import java.io.Serializable;

/**
 * Tag API model
 *
 * @author Jamie Cruwys
 */
public class Tag implements Serializable
{
	private String name;
	private String tag;

	/**
	 * Getter that ensures that we consistently have a hashtag as the starting character of each tag
	 */
	public String getName()
	{
		if (name == null)
		{
			name = "";
		}

		name = name.trim();

		if (!name.startsWith("#"))
		{
			name = "#" + name;
		}

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
}