package com.cube.lush.player.mobile.events;

import android.support.annotation.NonNull;

import com.lush.player.api.model.Event;

/**
 * Event Tab Selection
 * @author Jamie Cruwys.
 */
public interface EventTabSelection
{
	void selectTab(@NonNull Event event);
}