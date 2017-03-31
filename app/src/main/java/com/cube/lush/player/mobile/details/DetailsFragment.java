package com.cube.lush.player.mobile.details;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.brightcove.player.analytics.Analytics;
import com.brightcove.player.edge.PlaylistListener;
import com.brightcove.player.edge.VideoListener;
import com.brightcove.player.media.DeliveryType;
import com.brightcove.player.model.Playlist;
import com.brightcove.player.model.Video;
import com.brightcove.player.view.BaseVideoView;
import com.brightcove.player.view.BrightcoveExoPlayerVideoView;
import com.brightcove.player.view.BrightcovePlayerFragment;
import com.cube.lush.player.R;
import com.cube.lush.player.api.model.ContentType;
import com.cube.lush.player.api.model.MediaContent;
import com.cube.lush.player.content.handler.ResponseHandler;
import com.cube.lush.player.content.manager.MediaManager;
import com.cube.lush.player.content.model.VideoInfo;
import com.cube.lush.player.mobile.MainActivity;
import com.cube.lush.player.mobile.playback.PlaybackActivity;
import com.cube.lush.player.mobile.playback.PlaybackFragment;
import com.squareup.picasso.Picasso;

import org.apmem.tools.layouts.FlowLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailsFragment extends Fragment
{
	@SuppressWarnings("HardCodedStringLiteral")
	private static final String ARG_CONTENT = "arg_content";
	private MediaContent mediaContent;

	@BindView(R.id.thumbnail) ImageView thumbnail;
	@BindView(R.id.play) ImageView play;
	@BindView(R.id.content_type) TextView contentType;
	@BindView(R.id.title) TextView title;
	@BindView(R.id.description) TextView description;
	@BindView(R.id.toggle_description_length) Button toggleDescriptionButton;
	@BindView(R.id.tag_list) FlowLayout tagList;

	public DetailsFragment()
	{
		// Required empty public constructor
	}

	public static DetailsFragment newInstance(@NonNull MediaContent mediaContent)
	{
		DetailsFragment fragment = new DetailsFragment();
		Bundle args = new Bundle();
		args.putSerializable(ARG_CONTENT, mediaContent);
		fragment.setArguments(args);
		return fragment;
	}

	@Override public void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		mediaContent = (MediaContent)getArguments().getSerializable(ARG_CONTENT);

		if (mediaContent == null)
		{
			throw new RuntimeException("No media content to show details for");
		}
	}

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.mobile_fragment_details, container, false);
		ButterKnife.bind(this, view);

		return view;
	}

	@Override public void onActivityCreated(@Nullable Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		if (savedInstanceState == null)
		{
			populateUi();
		}
	}

	private void populateUi()
	{
		contentType.setText(mediaContent.getType().getName());
		title.setText(mediaContent.getTitle());
		description.setText(mediaContent.getDescription());

		// Populate image
		Picasso.with(thumbnail.getContext())
			.load(mediaContent.getThumbnail())
			.into(thumbnail);

		// Populate tags ui
		tagList.removeAllViews();
		LayoutInflater inflater = LayoutInflater.from(tagList.getContext());

		for (final String tag : mediaContent.getTags())
		{
			View view = inflater.inflate(R.layout.mobile_item_tag, tagList, false);
			TextView text = (TextView)view.findViewById(R.id.text);
			text.setText(tag);

			view.setOnClickListener(new View.OnClickListener()
			{
				@Override public void onClick(View view)
				{
					onTagClicked(view, tag);
				}
			});

			tagList.addView(view);
		}
	}

	private void onTagClicked(@NonNull View view, @NonNull String tag)
	{
		Toast.makeText(view.getContext(), "Tag clicked: " + tag, Toast.LENGTH_SHORT).show();
	}

	@OnClick(R.id.play) void onPlayClicked()
	{
		Context context = play.getContext();

		Toast.makeText(context, "Play clicked", Toast.LENGTH_SHORT).show();
		context.startActivity(PlaybackActivity.getIntent(context, mediaContent));
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
		Toast.makeText(title.getContext(), "Share clicked ", Toast.LENGTH_SHORT).show();
	}
}