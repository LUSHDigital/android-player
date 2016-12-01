package com.cube.lush.player;

import android.support.annotation.NonNull;

import com.cube.lush.player.model.MediaContent;

/**
 * @author Jamie Cruwys
 * @project lush-player-android-client
 */
public interface RevealCallback
{
	/**
	 * Called when we have received the media content from another activity or from the API
	 * @param mediaContent
	 */
	void loadMediaContent(@NonNull MediaContent mediaContent);

	/**
	 * Called when the media content
	 */
	/**
	 * Called
	 * The content for the page has been loaded. The content should now be visible and the progress bar hidden
	 */
	void onMediaContentLoaded();

	/**
	 * Called when the media for the entire page has been loaded, except for the hidden content. This should now load.
	 */
	void loadHiddenMediaContent(@NonNull MediaContent item);

	/**
	 * Called when the hidden content has loaded and should now be revealed
	 */
	void revealHiddenMediaContent();
}