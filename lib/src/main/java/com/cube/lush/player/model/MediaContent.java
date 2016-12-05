package com.cube.lush.player.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * Model object representing generic media content.
 *
 * @author Jamie Cruwys
 * @project lush-player-android-client
 */
@Data
public class MediaContent implements Serializable
{
	private String title;
	private String id;
	private String description;
	private Date date;
	private String thumbnail;
	private ContentType type;
}
