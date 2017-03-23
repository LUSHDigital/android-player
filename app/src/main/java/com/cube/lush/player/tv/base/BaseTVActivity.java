package com.cube.lush.player.tv.base;

import android.content.Intent;

import com.cube.lush.player.common.BaseActivity;
import com.cube.lush.player.tv.search.SearchActivity;

/**
 * Base activity that performs actions common to all activities in the app.
 */
public class BaseTVActivity extends BaseActivity
{
	@Override
	public boolean onSearchRequested()
	{
		startActivity(new Intent(this, SearchActivity.class));
		return true;
	}
}