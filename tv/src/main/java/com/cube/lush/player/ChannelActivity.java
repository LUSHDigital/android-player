package com.cube.lush.player;

import android.os.Bundle;

public class ChannelActivity extends LushActivity
{
	public static final String EXTRA_CHANNEL = "extra_channel";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_channel);
	}
}
