package com.lush.player.api.model;

import java.io.Serializable;

/**
 * Channel API model
 *
 * @author Jamie Cruwys
 */
public class Channel implements Serializable
{
	private String name;
	private String tag;
	private String image;

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

	public String getImage()
	{
		return image;
	}

	public void setImage(String image)
	{
		this.image = image;
	}
}