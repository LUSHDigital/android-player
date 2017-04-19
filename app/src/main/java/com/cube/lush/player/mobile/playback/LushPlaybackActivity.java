package com.cube.lush.player.mobile.playback;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.brightcove.player.analytics.Analytics;
import com.brightcove.player.mediacontroller.BrightcoveMediaController;
import com.brightcove.player.model.Video;
import com.brightcove.player.view.BrightcoveExoPlayerVideoView;
import com.brightcove.player.view.BrightcovePlayer;
import com.cube.lush.player.R;
import com.cube.lush.player.api.model.MediaContent;
import com.cube.lush.player.content.manager.MediaManager;

import static com.cube.lush.player.mobile.playback.PlaybackActivity.EXTRA_MEDIA_CONTENT;

/**
 * Created by Jamie Cruwys of 3 SIDED CUBE on 18/04/2017.
 */
public class LushPlaybackActivity extends BrightcovePlayer
{
	public static Intent getIntent(@NonNull Context context, @Nullable MediaContent mediaContent)
	{
		Intent intent = new Intent(context, LushPlaybackActivity.class);

		if (mediaContent != null)
		{
			intent.putExtra(EXTRA_MEDIA_CONTENT, mediaContent);
		}

		return intent;
	}

	@Override protected void onCreate(Bundle savedInstanceState)
	{
		// When extending the BrightcovePlayer, we must assign the BrightcoveVideoView before
		// entering the superclass. This allows for some stock video player lifecycle
		// management.  Establish the video object and use it's event emitter to get important
		// notifications and to control logging.
		setContentView(R.layout.playback_fragment);
		brightcoveVideoView = (BrightcoveExoPlayerVideoView) findViewById(R.id.brightcove_video_view);
		brightcoveVideoView.setMediaController(new BrightcoveMediaController(brightcoveVideoView, R.layout.one_line_brightcove_media_controller));
		super.onCreate(savedInstanceState);

		Analytics analytics = brightcoveVideoView.getAnalytics();
		analytics.setAccount(com.cube.lush.player.content.BuildConfig.BRIGHTCOVE_ACCOUNT_ID);

		MediaManager.getInstance().getCatalog().findVideoByID("5321597550001", new com.brightcove.player.edge.VideoListener()
		{
			@Override public void onVideo(Video video)
			{
				brightcoveVideoView.add(video);
				brightcoveVideoView.start();
				brightcoveVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
				{
					@Override
					public void onCompletion(MediaPlayer mediaPlayer)
					{
						finish();
					}
				});
			}
		});
	}
}