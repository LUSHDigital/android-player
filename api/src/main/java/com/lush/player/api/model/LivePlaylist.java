package com.cube.lush.player.api.model;

import java.io.Serializable;

/**
 * LivePlaylist API model
 *
 * @author Jamie Cruwys
 */
public class LivePlaylist implements Serializable
{
	private String id;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}
}