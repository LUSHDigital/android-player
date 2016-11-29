package com.cube.lush.player.handler;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

/**
 * @author Jamie Cruwys
 * @project lush-player-android-client
 */
public interface ResponseHandler<T>
{
	void onSuccess(@NonNull List<T> items);
	void onFailure(@Nullable Throwable t);
}