package com.cube.lush.player;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

public class PlaybackActivity extends LushActivity
{
	public static final String ARGUMENT_PLAYBACK_METHOD = "playback_method";
	public static final String ARGUMENT_PLAYBACK_METHOD_VALUE = "playback_method_value";

	public static Intent getIntent(@NonNull Context context, @Nullable PlaybackMethod playbackMethod, @Nullable String playbackMethodValue)
	{
		Intent intent = new Intent(context, PlaybackActivity.class);

		if (playbackMethod != null)
		{
			intent.putExtra(ARGUMENT_PLAYBACK_METHOD, playbackMethod);
		}

		if (!TextUtils.isEmpty(playbackMethodValue))
		{
			intent.putExtra(ARGUMENT_PLAYBACK_METHOD_VALUE, playbackMethodValue);
		}

		return intent;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_playback);
	}
}
