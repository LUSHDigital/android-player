package com.cube.lush.player.mobile.events;

import android.support.annotation.NonNull;

import com.cube.lush.player.mobile.events.EventTab;

/**
 * Created by Jamie Cruwys.
 */
public interface EventTabSelection
{
	void selectTab(@NonNull EventTab tab);
}
