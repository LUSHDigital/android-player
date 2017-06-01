package com.cube.lush.player.mobile.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Types of content. This is for use with {@link com.cube.lush.player.api.LushAPI#getCategories(String, String)} requests, and distinct from {@link ContentType}
 * which is for more general use.
 *
 * @author Jamie Cruwys
 */
@AllArgsConstructor
public enum  ContentTypeTab
{
	ALL("All Episodes"),
	TV("TV"),
	RADIO("Radio");

	/**
	 * The name used for tabs when you want to filter by category content type
	 */
	@Getter private String name;

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