package com.lush.player.api.model;

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

	public Tag() { }

	public Tag(String name, String tag)
	{
		this.name = name;
		this.tag = tag;
	}

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

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Tag tag1 = (Tag) o;

		if (name != null ? !name.equals(tag1.name) : tag1.name != null) return false;
		return tag != null ? tag.equals(tag1.tag) : tag1.tag == null;

	}

	@Override
	public int hashCode()
	{
		int result = name != null ? name.hashCode() : 0;
		result = 31 * result + (tag != null ? tag.hashCode() : 0);
		return result;
	}
}