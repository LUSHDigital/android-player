package com.cube.lush.player.tv.channels;

import android.os.Bundle;

import com.cube.lush.player.tv.base.BaseTVActivity;
import com.cube.lush.player.R;

public class ChannelActivity extends BaseTVActivity
{
	public static final String EXTRA_CHANNEL = "extra_channel";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_channel);
	}
}
