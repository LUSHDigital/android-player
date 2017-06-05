package com.cube.lush.player.tv.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.cube.lush.player.tv.search.SearchActivity;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Base activity that performs actions common to all activities in the app.
 *
 * @author Jamie Cruwys
 */
public class BaseTVActivity extends Activity
{
	@Override
	protected void attachBaseContext(Context newBase)
	{
		// Context wrapper to apply custom fonts
		super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
	}

	@Override
	public boolean onSearchRequested()
	{
		startActivity(new Intent(this, SearchActivity.class));
		return true;
	}
}