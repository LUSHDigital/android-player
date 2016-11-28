package com.cube.lush.player.model;

import lombok.Getter;

/**
 * Created by tim on 28/11/2016.
 */
public enum Channel
{
	LUSH_LIFE("lushlife", "Lush Life", ""),
	LUSH_KITCHEN("kitchen", "Lush Kitchen", ""),
	LUSH_TIMES("times", "Lush Times", ""),
	SOAPBOX("soapbox", "Soapbox", ""),
	GORILLA("gorilla", "Gorilla", ""),
	LUSH_COSMETICS("cosmetics", "Lush Cosmetics", "");

	@Getter
	private String id;

	@Getter
	private String title;

	@Getter
	private String description;

	Channel(String id, String title, String description)
	{
		this.id = id;
		this.title = title;
		this.description = description;
	}
}
