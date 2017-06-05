package com.cube.lush.player.api.model;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Content type API enum
 *
 * @author Jamie Cruwys
 */
@AllArgsConstructor
public enum ContentType
{
	@SerializedName("tv")
	TV("TV"),

	@SerializedName("radio")
	RADIO("RADIO");

	@Getter private String name;
}