package com.cube.lush.player.mobile.details;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cube.lush.player.R;
import com.cube.lush.player.api.model.MediaContent;
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
		description.setText(mediaContent.getDescription().trim());

		// Populate image
		Picasso.with(thumbnail.getContext())
			.load(mediaContent.getThumbnail())
			.into(thumbnail);

		List<String> tags = mediaContent.getTags();

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

	private void showTags(@NonNull List<String> tags)
	{
		// Populate tags ui
		tagList.removeAllViews();
		LayoutInflater inflater = LayoutInflater.from(tagList.getContext());

		for (final String tag : tags)
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

		tagSection.setVisibility(View.VISIBLE);
	}

	private void hideTags()
	{
		tagSection.setVisibility(View.GONE);
	}

	private void onTagClicked(@NonNull View view, @NonNull String tag)
	{
		Toast.makeText(view.getContext(), "Tag clicked: " + tag, Toast.LENGTH_SHORT).show();
	}

	@OnClick(R.id.play) void onPlayClicked()
	{
		Toast.makeText(getContext(), "Play clicked", Toast.LENGTH_SHORT).show();
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
		shareIntent.putExtra(Intent.EXTRA_TEXT, mediaContent.getWebLink());
		shareIntent.setType("text/plain");

		startActivity(Intent.createChooser(shareIntent, getString(R.string.share_via)));
	}
}