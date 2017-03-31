package com.cube.lush.player.mobile.playback;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.cube.lush.player.R;
import com.cube.lush.player.api.model.MediaContent;

/**
 * Created by Jamie Cruwys of 3 SIDED CUBE on 31/03/2017.
 */
public class PlaybackActivity extends AppCompatActivity
{
	@SuppressWarnings("HardCodedStringLiteral")
	public static final String EXTRA_MEDIA_CONTENT = "media_content";

	public static Intent getIntent(@NonNull Context context, @Nullable MediaContent mediaContent)
	{
		Intent intent = new Intent(context, PlaybackActivity.class);

		if (mediaContent != null)
		{
			intent.putExtra(EXTRA_MEDIA_CONTENT, mediaContent);
		}

		return intent;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mobile_activity_playback);
	}
}