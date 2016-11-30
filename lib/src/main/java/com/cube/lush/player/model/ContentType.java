package com.cube.lush.player.model;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Jamie Cruwys
 * @project lush-player-android-client
 */
@AllArgsConstructor
public enum ContentType
{
	@SerializedName("tv")
	TV("tv"),

	@SerializedName("radio")
	RADIO("radio");

	@Getter private String name;
}