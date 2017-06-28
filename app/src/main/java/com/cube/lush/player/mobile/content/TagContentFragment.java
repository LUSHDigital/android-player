package com.cube.lush.player.mobile.content;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;

import com.cube.lush.player.R;
import com.lush.player.api.model.Programme;
import com.lush.player.api.model.Tag;
import com.cube.lush.player.content.handler.ResponseHandler;
import com.cube.lush.player.content.repository.TaggedProgrammeRepository;
import com.cube.lush.player.content.util.MediaSorter;
import com.cube.lush.player.mobile.base.BaseContentFragment;
import com.cube.lush.player.mobile.model.ProgrammeFilterOption;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import uk.co.jamiecruwys.contracts.ListingData;

/**
 * Tag Content Fragment
 *
 * @author Jamie Cruwys
 */
public class TagContentFragment extends BaseContentFragment
{
	@SuppressWarnings("HardCodedStringLiteral")
	private static final String ARG_TAG = "arg_tag";

	private Tag tag;

	public TagContentFragment()
	{
		// Required empty public constructor
	}

	public static TagContentFragment newInstance(@NonNull Tag tag)
	{
		TagContentFragment fragment = new TagContentFragment();
		Bundle args = new Bundle();
		args.putSerializable(ARG_TAG, tag);
		fragment.setArguments(args);
		return fragment;
	}

	@Override public void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		tag = (Tag)getArguments().getSerializable(ARG_TAG);
	}

	@Override public void getListDataForFilterOption(@NonNull final ProgrammeFilterOption filterOption, @NonNull final ListingData callback)
	{
		TaggedProgrammeRepository.getInstance(getContext()).setTag(tag.getTag());
		TaggedProgrammeRepository.getInstance(getContext()).getItems(new ResponseHandler<Programme>()
		{
			@Override public void onSuccess(@NonNull List<Programme> items)
			{
				if (filterOption == ProgrammeFilterOption.ALL)
				{
					MediaSorter.MOST_RECENT_FIRST.sort(items);
					callback.onListingDataRetrieved(items);
				}
				else if (filterOption == ProgrammeFilterOption.TV)
				{
					Set<Programme> videos = TaggedProgrammeRepository.getInstance(getContext()).getVideos();

					List videosList = new ArrayList();
					videosList.addAll(videos);
					MediaSorter.MOST_RECENT_FIRST.sort(videosList);

					callback.onListingDataRetrieved(videosList);
				}
				else if (filterOption == ProgrammeFilterOption.RADIO)
				{
					Set<Programme> radios = TaggedProgrammeRepository.getInstance(getContext()).getRadios();

					List radiosList = new ArrayList();
					radiosList.addAll(radios);
					MediaSorter.MOST_RECENT_FIRST.sort(radiosList);

					callback.onListingDataRetrieved(radiosList);
				}
			}

			@Override public void onFailure(@Nullable Throwable t)
			{
				callback.onListingDataError(t);
			}
		});
	}

	@NonNull @Override public String provideContentTitle()
	{
		return "Tag: " + tag.getName();
	}

	@NonNull @Override public LinearLayoutManager provideLayoutManagerForFilterOption(ProgrammeFilterOption filterOption)
	{
		final int NUMBER_COLUMNS = getResources().getInteger(R.integer.tag_content_columns);
		return new GridLayoutManager(getContext(), NUMBER_COLUMNS);
	}

	@Override public void onSaveInstanceState(Bundle outState)
	{
		outState.putSerializable(ARG_TAG, tag);
		super.onSaveInstanceState(outState);
	}
}