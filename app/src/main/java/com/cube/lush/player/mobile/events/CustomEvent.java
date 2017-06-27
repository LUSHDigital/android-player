package com.cube.lush.player.mobile.events;

import android.support.annotation.NonNull;

import com.lush.player.api.model.Event;

import java.util.Date;

/**
 * <Class Description>
 *
 * @author Jamie Cruwys
 */
public class CustomEvent extends Event
{
	protected String name;
	protected String tag;
	protected Date startDate;
	protected Date endDate;

	public CustomEvent(@NonNull String name)
	{
		this.name = name;
		this.tag = name;

		long now = System.currentTimeMillis();
		long day = 1000 * 60 * 60 * 24;
		long dayLater = now + day;

		startDate = new Date(now);
		endDate = new Date(dayLater);
	}
}