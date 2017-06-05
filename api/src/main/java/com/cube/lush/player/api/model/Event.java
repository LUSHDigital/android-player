package com.cube.lush.player.api.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * Event API model
 *
 * @author Jamie Cruwys
 */
@Data
public class Event implements Serializable
{
	private String name;
	private String tag;
	private Date startDate;
	private Date endDate;
}