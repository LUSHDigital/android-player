package com.cube.lush.player.api.model;

import java.io.Serializable;

import lombok.Data;

/**
 * Channel API model
 *
 * @author Jamie Cruwys
 */
@Data
public class Channel implements Serializable
{
	private String name;
	private String tag;
	private String image;
}