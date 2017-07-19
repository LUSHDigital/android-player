package com.lush.player.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Content type API enum
 *
 * @author Jamie Cruwys
 */
public enum ContentType
{
	@SerializedName("tv")
	TV("TV"),

	@SerializedName("radio")
	RADIO("RADIO");

	private String name;

	ContentType(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}
}