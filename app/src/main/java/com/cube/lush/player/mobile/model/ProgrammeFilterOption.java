package com.cube.lush.player.mobile.model;

import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Filter options for lists of programmes
 *
 * @author Jamie Cruwys
 */
@AllArgsConstructor
public enum ProgrammeFilterOption
{
	ALL("All Episodes"),
	TV("TV"),
	RADIO("Radio");

	@Getter private String name;

	public static List<ProgrammeFilterOption> listValues()
	{
		return Arrays.asList(ProgrammeFilterOption.values());
	}
}