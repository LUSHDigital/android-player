package com.cube.lush.player.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Jamie Cruwys
 * @project lush-player-android-client
 */
@AllArgsConstructor
public enum CategoryContentType
{
	TV("tv_program"),
	RADIO("radio_program");

	@Getter private String name;
}