package com.cube.lush.player.tv.details;

import android.os.Bundle;

import com.cube.lush.player.tv.base.BaseTVActivity;
import com.cube.lush.player.R;

/**
 * Programme Details Activity
 *
 * @author Jamie Cruwys
 */
public class ProgrammeDetailsActivity extends BaseTVActivity
{
	@SuppressWarnings("HardCodedStringLiteral")
	public static final String EXTRA_PROGRAMME = "extra_media_item";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_media_details);
	}
}