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

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Channel channel = (Channel) o;

		if (name != null ? !name.equals(channel.name) : channel.name != null) return false;
		return tag != null ? tag.equals(channel.tag) : channel.tag == null;
	}

	@Override
	public int hashCode()
	{
		int result = name != null ? name.hashCode() : 0;
		result = 31 * result + (tag != null ? tag.hashCode() : 0);
		return result;
	}
}