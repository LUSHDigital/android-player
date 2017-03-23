package com.cube.lush.player.tv;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.cube.lush.player.tv.search.SearchActivity;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Base activity that performs actions common to all activities in the app.
 */
public class BaseTVActivity extends AppCompatActivity
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