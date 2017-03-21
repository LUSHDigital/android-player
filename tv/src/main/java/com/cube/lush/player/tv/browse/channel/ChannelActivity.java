package com.cube.lush.player.tv.browse.channel;

import android.os.Bundle;

import com.cube.lush.player.tv.LushActivity;
import com.cube.lush.player.tv.R;

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
