package com.cube.lush.player;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by tim on 24/11/2016.
 */
public class MediaDetailsActivity extends Activity
{
	public static final String EXTRA_MEDIA = "extra_media_item";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_media_details);
	}

}
