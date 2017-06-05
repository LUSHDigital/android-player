package com.cube.lush.player.api.model;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

/**
 * Tag API model
 *
 * @author Jamie Cruwys
 */
@Data
public class Tag implements Serializable
{
	@Getter(AccessLevel.NONE)
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
}