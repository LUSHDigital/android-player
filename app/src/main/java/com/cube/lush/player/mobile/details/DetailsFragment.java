package com.cube.lush.player.mobile.details;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.brightcove.player.analytics.Analytics;
import com.brightcove.player.appcompat.BrightcovePlayerFragment;
import com.brightcove.player.display.ExoPlayerVideoDisplayComponent;
import com.brightcove.player.display.VideoDisplayComponent;
import com.brightcove.player.edge.VideoListener;
import com.brightcove.player.media.DeliveryType;
import com.brightcove.player.mediacontroller.BrightcoveMediaController;
import com.brightcove.player.model.Video;
import com.brightcove.player.view.BaseVideoView;
import com.brightcove.player.view.BrightcoveExoPlayerVideoView;
import com.cube.lush.player.R;
import com.cube.lush.player.analytics.Track;
import com.cube.lush.player.content.brightcove.BrightcoveCatalog;
import com.cube.lush.player.content.repository.ProgrammeRepository;
import com.cube.lush.player.mobile.MainActivity;
import com.cube.lush.player.mobile.content.TagContentFragment;
import com.cube.lush.player.mobile.playback.LushPlaybackActivity;
import com.cube.lush.player.mobile.view.TagClickListener;
import com.cube.lush.player.mobile.view.TagSectionView;
import com.google.android.exoplayer.ExoPlaybackException;
import com.google.android.exoplayer.ExoPlayer;
import com.lush.player.api.model.ContentType;
import com.lush.player.api.model.Programme;
import com.lush.player.api.model.Tag;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Details Fragment
 *
 * @author Jamie Cruwys
 */
public class DetailsFragment extends BrightcovePlayerFragment
{
	@SuppressWarnings("HardCodedStringLiteral")
	private static final String ARG_CONTENT = "arg_content";
	private static final String ARG_WATCHED_MILLISECONDS = "arg_watched_milliseconds";
	private static final String ARG_PLAYING = "arg_playing";
	private Programme programme;

	public static final int REQUEST_CODE = 453;

	@BindView(R.id.playOverlay) ImageView playOverlay;
	@BindView(R.id.content_type) TextView contentType;
	@BindView(R.id.title) TextView title;
	@BindView(R.id.description) TextView description;
	@BindView(R.id.toggle_description_length) Button toggleDescriptionButton;
	@BindView(R.id.tag_section) TagSectionView tagSection;
	@BindView(R.id.brightcove_video_view) BrightcoveExoPlayerVideoView brightcoveExoPlayerVideoView;

	public DetailsFragment()
	{
		// Required empty public constructor
	}

	public static DetailsFragment newInstance(@NonNull Programme programme)
	{
		DetailsFragment fragment = new DetailsFragment();
		Bundle args = new Bundle();
		args.putSerializable(ARG_CONTENT, programme);
		fragment.setArguments(args);
		return fragment;
	}

	@Override public void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		programme = (Programme)getArguments().getSerializable(ARG_CONTENT);

		if (savedInstanceState != null)
		{
			programme = (Programme)savedInstanceState.getSerializable(ARG_CONTENT);
		}
	}

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.detail_loaded, container, false);
		baseVideoView = (BaseVideoView) view.findViewById(R.id.brightcove_video_view);

		// Our custom media controller, which is in one line
		BrightcoveMediaController brightcoveMediaController = new BrightcoveMediaController(baseVideoView, R.layout.one_line_brightcove_media_controller);
		baseVideoView.setMediaController(brightcoveMediaController);

		// Brightcove player onCreateView
		super.onCreateView(inflater, container, savedInstanceState);

		ButterKnife.bind(this, view);

		// Setup account credentials
		Analytics analytics = baseVideoView.getAnalytics();
		analytics.setAccount(com.cube.lush.player.content.BuildConfig.BRIGHTCOVE_ACCOUNT_ID);

		if (savedInstanceState != null)
		{
			int watchedMilliseconds = savedInstanceState.getInt(ARG_WATCHED_MILLISECONDS, 0);
			baseVideoView.seekTo(watchedMilliseconds);

			boolean wasPlaying = savedInstanceState.getBoolean(ARG_PLAYING, false);

			if (wasPlaying)
			{
				onPlayClicked();
			}
		}

		tagSection.setTagClickListener(new TagClickListener()
		{
			@Override
			public void onTagClick(@NonNull Tag tag)
			{
				((MainActivity)getActivity()).showFragment(TagContentFragment.newInstance(tag));
			}
		});

		return view;
	}

	@Override public void onActivityCreated(@Nullable Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		populateUi();
	}

	private void populateUi()
	{
		contentType.setText(programme.getType().getName());
		title.setText(programme.getTitle());

		if (!TextUtils.isEmpty(programme.getDescription()))
		{
			description.setText(programme.getDescription().trim());
		}

		loadBrightcoveStillImage();

		List<Tag> tags = programme.getTags();
		tagSection.setTags(tags);

		description.post(new Runnable()
		{
			@Override
			public void run()
			{
				int condensedMaxLines = getResources().getInteger(R.integer.description_condensed_lines);

				// If the text is truncated, show the "show full description" button
				if (description.getLineCount() > condensedMaxLines)
				{
					toggleDescriptionButton.setVisibility(View.VISIBLE);
				}
				else
				{
					toggleDescriptionButton.setVisibility(View.INVISIBLE);
				}

				description.setMaxLines(condensedMaxLines);
			}
		});
	}

	private void loadBrightcoveStillImage()
	{
		// TODO: Fix this
		try
		{
			// Load image into brightcove video view
			Picasso.with(baseVideoView.getContext())
					.load(programme.getThumbnail())
					.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
					.into(baseVideoView.getStillView());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@OnClick(R.id.playOverlay) void onPlayClicked()
	{
		playOverlay.setVisibility(View.GONE);

		String mediaId = programme.getId();

		if (programme.getType() == ContentType.TV)
		{
			// TV Content from Brightcove
			BrightcoveCatalog.INSTANCE.getCatalog().findVideoByID(mediaId, new VideoListener()
			{
				@Override
				public void onVideo(Video video)
				{
					playVideo(video);
				}

				@Override
				public void onError(String error)
				{
					playOverlay.setVisibility(View.VISIBLE);
					Toast.makeText(playOverlay.getContext(), "Error playing video, please try again later", Toast.LENGTH_SHORT).show();
					Log.e(DetailsFragment.class.getSimpleName(), "Brightcove findVideoByID error: " + error);
				}
			});
		}
		else if (programme.getType() == ContentType.RADIO)
		{
			// Radio Content from mp3 files, shown as videos in the brightcove player
			Video video = Video.createVideo(programme.getFile(), DeliveryType.MP4);
			playVideo(video);

			Track.event("Play", programme.getId());
		}

		ProgrammeRepository.watched(getContext(), programme);
	}

	public void playVideo(@NonNull Video video)
	{
		


		VideoDisplayComponent videoDisplay = brightcoveExoPlayerVideoView.getVideoDisplay();

		if (videoDisplay instanceof ExoPlayerVideoDisplayComponent)
		{
			ExoPlayerVideoDisplayComponent exoPlayerVideoDisplay = (ExoPlayerVideoDisplayComponent)videoDisplay;

			if (exoPlayerVideoDisplay != null)
			{
				ExoPlayer exoPlayer = exoPlayerVideoDisplay.getExoPlayer();

				if (exoPlayer != null)
				{
					exoPlayer.addListener(new ExoPlayer.Listener()
					{
						@Override
						public void onPlayerStateChanged(boolean b, int i)
						{
							// NO-OP
						}

						@Override
						public void onPlayWhenReadyCommitted()
						{
							// NO-OP
						}

						@Override
						public void onPlayerError(ExoPlaybackException e)
						{
							playOverlay.setVisibility(View.VISIBLE);
							Toast.makeText(playOverlay.getContext(), "Error playing video, please try again later", Toast.LENGTH_SHORT).show();
						}
					});
				}
			}
		}

		baseVideoView.add(video);
		baseVideoView.start();

		baseVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
		{
			@Override
			public void onPrepared(MediaPlayer mp)
			{
				Log.e(DetailsFragment.class.getSimpleName(), "On Prepared");
			}
		});

		baseVideoView.setOnInfoListener(new MediaPlayer.OnInfoListener()
		{
			@Override
			public boolean onInfo(MediaPlayer mp, int what, int extra)
			{
				return false;
			}
		});

		baseVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
		{
			@Override public void onCompletion(MediaPlayer mp)
			{
				baseVideoView.stopPlayback();
				playOverlay.setVisibility(View.VISIBLE);

				// TODO: Get the still view to show again
				loadBrightcoveStillImage();

				Track.event("End", programme.getId());
			}
		});
	}

	@OnClick(R.id.toggle_description_length) void onToggleDescriptionLengthClicked()
	{
		int maxLines = description.getMaxLines();

		int condensedMaxLines = getResources().getInteger(R.integer.description_condensed_lines);
		int expandedMaxLines = getResources().getInteger(R.integer.description_expanded_lines);

		if (maxLines <= condensedMaxLines)
		{
			description.setMaxLines(expandedMaxLines);
			toggleDescriptionButton.setText(R.string.show_less);
		}
		else
		{
			description.setMaxLines(condensedMaxLines);
			toggleDescriptionButton.setText(R.string.show_more);
		}
	}

	@OnClick(R.id.share) void onShareClicked()
	{
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		shareIntent.putExtra(Intent.EXTRA_TEXT, programme.getWebLink());
		shareIntent.setType("text/plain");

		startActivity(Intent.createChooser(shareIntent, getString(R.string.share_via)));

		Track.event("Share", programme.getId());
	}

	@OnClick(R.id.full_screen) void onFullscreenClicked()
	{
		// Use current seek position and pass it off to the playback activity/fragment to continue playback
		int currentSeekPosition = baseVideoView.getCurrentPosition();

		Intent fullscreenPlaybackIntent = LushPlaybackActivity.getIntent(getContext(), programme, currentSeekPosition);
		startActivityForResult(fullscreenPlaybackIntent, REQUEST_CODE);
	}

	public void seekTo(int milliseconds)
	{
		if (baseVideoView != null)
		{
			baseVideoView.seekTo(milliseconds);
		}
	}

	@Override public void onSaveInstanceState(Bundle bundle)
	{
		bundle.putSerializable(ARG_CONTENT, programme);
		bundle.putInt(ARG_WATCHED_MILLISECONDS, baseVideoView.getCurrentPosition());
		bundle.putBoolean(ARG_PLAYING, baseVideoView.isPlaying());

		super.onSaveInstanceState(bundle);
	}
}