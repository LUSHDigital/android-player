package com.cube.lush.player.content.handler;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

/**
 * Interface for outcomes given in response to a request for a list of items.
 *
 * @author Jamie Cruwys
 * @project lush-player-android-client
 */
public interface ResponseHandler<T>
{
	void onSuccess(@NonNull List<T> items);
	void onFailure(@Nullable Throwable t);
}
