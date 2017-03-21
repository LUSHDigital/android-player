package com.cube.lush.player.content.model;

import android.support.annotation.DrawableRes;

import com.cube.lush.player.content.R;

import lombok.Getter;

/**
 * Information about Lush content channels
 * Created by tim on 28/11/2016.
 */
public enum Channel
{
	LUSH_LIFE("lushlife", "Lush Life", "", R.drawable.channel_lush_life),
	LUSH_KITCHEN("kitchen", "Lush Kitchen", "", R.drawable.channel_lush_kitchen),
	LUSH_TIMES("times", "Lush Times", "", R.drawable.channel_lush_times),
	SOAPBOX("soapbox", "Soapbox", "", R.drawable.channel_soapbox),
	GORILLA("gorilla", "Gorilla", "", R.drawable.channel_gorilla),
	LUSH_COSMETICS("cosmetics", "Lush Cosmetics", "", R.drawable.channel_lush_cosmetics);

	/**
	 * This ID can be used with {@link com.cube.lush.player.api.LushAPI#getCategories(String, String)}
	 */
	@Getter
	private String id;
	@Getter
	private String title;
	@Getter
	private String description;
	@DrawableRes
	@Getter
	private int logo;

	Channel(String id, String title, String description, @DrawableRes int logo)
	{
		this.id = id;
		this.title = title;
		this.description = description;
		this.logo = logo;
	}
}
