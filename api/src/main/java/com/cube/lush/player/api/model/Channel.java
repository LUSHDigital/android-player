package com.cube.lush.player.api.model;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by Jamie Cruwys of 3 SIDED CUBE on 31/05/2017.
 */
@Data
public class Channel implements Serializable
{
	private String name;
	private String tag;
	private String image;
}