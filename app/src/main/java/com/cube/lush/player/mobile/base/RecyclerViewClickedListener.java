package com.cube.lush.player.mobile.base;

import android.support.annotation.NonNull;

/**
 * Created by Jamie Cruwys of 3 SIDED CUBE on 04/04/2017.
 */
public interface RecyclerViewClickedListener<ITEMTYPE>
{
	void onRecyclerViewItemClicked(@NonNull ITEMTYPE item);
}