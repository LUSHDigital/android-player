package com.cube.lush.player;

import android.os.Bundle;

/**
 * Created by tim on 24/11/2016.
 */
public class MediaDetailsActivity extends LushActivity
{
	public static final String EXTRA_MEDIA = "extra_media_item";
	public static final String EXTRA_MEDIA_ID = "extra_media_item_id";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_media_details);
	}
}
