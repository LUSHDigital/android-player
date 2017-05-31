package com.cube.lush.player.api.model;

import java.io.Serializable;

import lombok.Data;

/**
 * Tag API model
 *
 * @author Jamie Cruwys
 */
@Data
public class Tag implements Serializable
{
	private String name;
	private String tag;
}