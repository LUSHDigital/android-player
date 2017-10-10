package com.cube.lush.player.mobile.model;

import java.util.Arrays;
import java.util.List;

/**
 * Filter options for lists of programmes
 *
 * @author Jamie Cruwys
 */
public enum ProgrammeFilterOption
{
	ALL("All Episodes"),
	TV("TV"),
	RADIO("Radio");

	private String name;

	ProgrammeFilterOption(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public static List<ProgrammeFilterOption> listValues()
	{
		return Arrays.asList(ProgrammeFilterOption.values());
	}
}