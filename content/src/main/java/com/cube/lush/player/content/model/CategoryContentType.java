package com.cube.lush.player.content.model;

import java.util.ArrayList;
import java.util.List;

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
	@SuppressWarnings("HardCodedStringLiteral")
	ALL(null, "All Episodes"),

	@SuppressWarnings("HardCodedStringLiteral")
	TV("tv_program", "TV"),

	@SuppressWarnings("HardCodedStringLiteral")
	RADIO("radio_programme", "Radio");

	/**
	 * The name the Lush API expects on the {@link com.cube.lush.player.api.LushAPI#getCategories(String, String)} endpoint.
	 */
	@Getter
	private String apiContentType;

	/**
	 * The name used for tabs when you want to filter by category content type
	 */
	@Getter
	private String displayName;

	public static List<CategoryContentType> listValues()
	{
		ArrayList<CategoryContentType> items = new ArrayList<>();

		for (CategoryContentType item : CategoryContentType.values())
		{
			items.add(item);
		}

		return items;
	}
}