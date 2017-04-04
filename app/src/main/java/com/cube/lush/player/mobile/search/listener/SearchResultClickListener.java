package com.cube.lush.player.mobile.search.listener;

import android.support.annotation.NonNull;

import com.cube.lush.player.api.model.SearchResult;

/**
 * Created by Jamie Cruwys of 3 SIDED CUBE on 04/04/2017.
 */
public interface SearchResultClickListener
{
	void selectedSearchResult(@NonNull SearchResult searchResult);
}