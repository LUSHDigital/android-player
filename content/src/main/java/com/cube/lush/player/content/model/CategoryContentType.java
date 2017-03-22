package com.cube.lush.player.content.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Types of content. This is for use with {@link com.cube.lush.player.api.LushAPI#getCategories(String, String)} requests, and distinct from {@link ContentType}
 * which is for more general use.
 *
 * @author Jamie Cruwys
 * @project lush-player-android-client
 */
@AllArgsConstructor
public enum CategoryContentType
{
	TV("tv_program"),
	RADIO("radio_programme");

	/**
	 * The name the Lush API expects on the {@link com.cube.lush.player.api.LushAPI#getCategories(String, String)} endpoint.
	 */
	@Getter
	private String name;
}
