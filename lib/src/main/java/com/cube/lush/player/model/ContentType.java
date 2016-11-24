package com.cube.lush.player.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Jamie Cruwys
 * @project lush-player-android-client
 */
@AllArgsConstructor
enum ContentType
{
	TV("tv"),
	RADIO("radio");

	@Getter private String name;
}