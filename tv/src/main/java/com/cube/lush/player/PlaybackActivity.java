package com.cube.lush.player;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class PlaybackActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_playback);
	}

	@Override
	public boolean onSearchRequested()
	{
		startActivity(new Intent(this, SearchActivity.class));
		return true;
	}
}
