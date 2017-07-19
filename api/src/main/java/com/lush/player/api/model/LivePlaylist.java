package com.lush.player.api.model;

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

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		LivePlaylist that = (LivePlaylist) o;

		return id != null ? id.equals(that.id) : that.id == null;

	}

	@Override
	public int hashCode()
	{
		return id != null ? id.hashCode() : 0;
	}
}