package com.cube.lush.player.mobile.playback;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import com.brightcove.player.analytics.Analytics;
import com.brightcove.player.appcompat.BrightcovePlayerActivity;
import com.brightcove.player.edge.VideoListener;
import com.brightcove.player.media.DeliveryType;
import com.brightcove.player.mediacontroller.BrightcoveMediaController;
import com.brightcove.player.model.Video;
import com.brightcove.player.view.BaseVideoView;
import com.cube.lush.player.R;
import com.cube.lush.player.api.model.ContentType;
import com.cube.lush.player.api.model.Programme;
import com.cube.lush.player.content.brightcove.BrightcoveCatalog;
import com.cube.lush.player.content.repository.ProgrammeRepository;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Lush Playback Activity
 *
 * @author Jamie Cruwys
 */
public class LushPlaybackActivity extends BrightcovePlayerActivity
{
	@SuppressWarnings("HardCodedStringLiteral")
	public static final String EXTRA_MEDIA_CONTENT = "media_content";

	@SuppressWarnings("HardCodedStringLiteral")
	public static final String EXTRA_START_TIME = "start_time";

	public static final int RESULT_CODE = 236;

	@NonNull private Programme programme;
	@IntRange(from = 0) private int startTimeMilliseconds;

	public static Intent getIntent(@NonNull Context context, @NonNull Programme mediaContent, @IntRange(from = 0) int startTimeMilliseconds)
	{
		Intent intent = new Intent(context, LushPlaybackActivity.class);
		intent.putExtra(EXTRA_MEDIA_CONTENT, mediaContent);
		intent.putExtra(EXTRA_START_TIME, startTimeMilliseconds);
		return intent;
	}

	@Override protected void onCreate(Bundle savedInstanceState)
	{
		setContentView(R.layout.playback_fragment);
		baseVideoView = (BaseVideoView) findViewById(R.id.brightcove_video_view);

		// Our custom media controller, which is in one line
		BrightcoveMediaController brightcoveMediaController = new BrightcoveMediaController(baseVideoView, R.layout.one_line_brightcove_media_controller);
		baseVideoView.setMediaController(brightcoveMediaController);

		super.onCreate(savedInstanceState);

		ButterKnife.bind(this);

		Analytics analytics = baseVideoView.getAnalytics();
		analytics.setAccount(com.cube.lush.player.content.BuildConfig.BRIGHTCOVE_ACCOUNT_ID);

		programme = (Programme)getIntent().getSerializableExtra(EXTRA_MEDIA_CONTENT);
		startTimeMilliseconds = getIntent().getIntExtra(EXTRA_START_TIME, 0);
	}

	@Override protected void onRestoreInstanceState(Bundle savedInstanceState)
	{
		super.onRestoreInstanceState(savedInstanceState);
		startTimeMilliseconds = savedInstanceState.getInt(EXTRA_START_TIME);
	}

	@Override protected void onResume()
	{
		super.onResume();

		if (programme.getType() == ContentType.RADIO)
		{
			// Load image into brightcove video view
			Picasso.with(baseVideoView.getContext())
				.load(programme.getThumbnail())
				.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
				.into(baseVideoView.getStillView());
		}

		playMediaContent(programme, startTimeMilliseconds);
	}

	private void playMediaContent(@NonNull Programme mediaContent, @IntRange(from = 0) int startTimeMilliseconds)
	{
		if (mediaContent.getType() == ContentType.TV)
		{
			playTVContent(mediaContent, startTimeMilliseconds);
		}
		else if (mediaContent.getType() == ContentType.RADIO)
		{
			playRadioContent(mediaContent, startTimeMilliseconds);
		}
	}

	private void playTVContent(@NonNull Programme tv, @IntRange(from = 0) final int startTimeMilliseconds)
	{
		// TV Content from Brightcove

		BrightcoveCatalog.INSTANCE.getCatalog().findVideoByID(tv.getId(), new VideoListener()
		{
			@Override
			public void onVideo(Video video)
			{
				playVideo(video, startTimeMilliseconds);
			}

			@Override public void onError(String error)
			{
				super.onError(error);
			}
		});
	}

	private void playRadioContent(@NonNull Programme radio, @IntRange(from = 0) final int startTimeMilliseconds)
	{
		// Radio Content from mp3 files, shown as videos in the brightcove player
		Video video = Video.createVideo(radio.getFile(), DeliveryType.MP4);
		playVideo(video, startTimeMilliseconds);
	}

	private void playVideo(@NonNull Video video, @IntRange(from = 0) int startTimeMilliseconds)
	{
		// Mark programme as watched
		ProgrammeRepository.watched(programme);

		baseVideoView.add(video);
		baseVideoView.start();

		if (startTimeMilliseconds > 0)
		{
			baseVideoView.seekTo(startTimeMilliseconds);
		}

		baseVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
		{
			@Override public void onCompletion(MediaPlayer mp)
			{
				finish();
			}
		});
	}

	@OnClick(R.id.full_screen) void onFullscreenClicked()
	{
		// Pass back the current time to anyone who starts this activity for a result
		Intent intent = new Intent();
		intent.putExtra(EXTRA_MEDIA_CONTENT, programme);
		intent.putExtra(EXTRA_START_TIME, baseVideoView.getCurrentPosition());
		setResult(RESULT_CODE, intent);
		finish();
	}

	@Override
	protected void onSaveInstanceState(Bundle savedInstanceState)
	{
		savedInstanceState.putInt(EXTRA_START_TIME, baseVideoView.getCurrentPosition());
		super.onSaveInstanceState(savedInstanceState);
	}
}