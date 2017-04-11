package com.cube.lush.player.mobile.playback;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.cube.lush.player.R;
import com.cube.lush.player.api.model.MediaContent;
import com.cube.lush.player.mobile.base.BaseMobileActivity;

import uk.co.jamiecruwys.ViewState;

/**
 * Created by Jamie Cruwys of 3 SIDED CUBE on 31/03/2017.
 */
public class PlaybackActivity extends BaseMobileActivity
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

	@Override public int provideLoadingLayout()
	{
		return R.layout.playback_loading;
	}

	@Override public int provideEmptyLayout()
	{
		return R.layout.playback_empty;
	}

	@Override public int provideLoadedLayout()
	{
		return R.layout.playback_loaded;
	}

	@Override public int provideErrorLayout()
	{
		return R.layout.playback_error;
	}

	@Override public ViewState provideInitialViewState()
	{
		return ViewState.LOADING;
	}
}