package com.cube.lush.player.mobile.details;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.brightcove.player.analytics.Analytics;
import com.brightcove.player.appcompat.BrightcovePlayerFragment;
import com.brightcove.player.edge.VideoListener;
import com.brightcove.player.media.DeliveryType;
import com.brightcove.player.mediacontroller.BrightcoveMediaController;
import com.brightcove.player.model.Video;
import com.brightcove.player.view.BaseVideoView;
import com.cube.lush.player.R;
import com.cube.lush.player.analytics.Track;
import com.cube.lush.player.api.model.ContentType;
import com.cube.lush.player.api.model.Programme;
import com.cube.lush.player.api.model.Tag;
import com.cube.lush.player.content.brightcove.BrightcoveCatalog;
import com.cube.lush.player.content.repository.ProgrammeRepository;
import com.cube.lush.player.mobile.MainActivity;
import com.cube.lush.player.mobile.content.TagContentFragment;
import com.cube.lush.player.mobile.playback.LushPlaybackActivity;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.apmem.tools.layouts.FlowLayout;

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

	@BindView(R.id.playOverlay) ImageView playOverlay;
	@BindView(R.id.content_type) TextView contentType;
	@BindView(R.id.title) TextView title;
	@BindView(R.id.description) TextView description;
	@BindView(R.id.toggle_description_length) Button toggleDescriptionButton;
	@BindView(R.id.tag_list) FlowLayout tagList;
	@BindView(R.id.tag_section) LinearLayout tagSection;

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

		if (tags.isEmpty())
		{
			hideTags();
		}
		else
		{
			showTags(tags);
		}

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
		// Load image into brightcove video view
		Picasso.with(baseVideoView.getContext())
			.load(programme.getThumbnail())
			.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
			.into(baseVideoView.getStillView());
	}

	private void showTags(@NonNull List<Tag> tags)
	{
		// Populate tags ui
		tagList.removeAllViews();
		LayoutInflater inflater = LayoutInflater.from(tagList.getContext());

		for (final Tag tag : tags)
		{
			View view = inflater.inflate(R.layout.tag_item, tagList, false);
			TextView text = (TextView)view.findViewById(R.id.text);
			text.setText(tag.getName());

			view.setOnClickListener(new View.OnClickListener()
			{
				@Override public void onClick(View view)
				{
					onTagClicked(view, tag);
				}
			});

			tagList.addView(view);
		}

		tagSection.setVisibility(View.VISIBLE);
	}

	private void hideTags()
	{
		tagSection.setVisibility(View.GONE);
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

				@Override public void onError(String error)
				{
					super.onError(error);
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

		ProgrammeRepository.watched(programme);
	}

	public void playVideo(@NonNull Video video)
	{
		baseVideoView.add(video);
		baseVideoView.start();
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

	private void onTagClicked(@NonNull View view, @NonNull Tag tag)
	{
		((MainActivity)getActivity()).showFragment(TagContentFragment.newInstance(tag));
	}

	@OnClick(R.id.full_screen) void onFullscreenClicked()
	{
		// Use current seek position and pass it off to the playback activity/fragment to continue playback
		int currentSeekPosition = baseVideoView.getCurrentPosition();

		Intent fullscreenPlaybackIntent = LushPlaybackActivity.getIntent(getContext(), programme, currentSeekPosition);
		startActivity(fullscreenPlaybackIntent);
	}

	@Override public void onSaveInstanceState(Bundle bundle)
	{
		bundle.putSerializable(ARG_CONTENT, programme);
		bundle.putInt(ARG_WATCHED_MILLISECONDS, baseVideoView.getCurrentPosition());
		bundle.putBoolean(ARG_PLAYING, baseVideoView.isPlaying());

		super.onSaveInstanceState(bundle);
	}
}