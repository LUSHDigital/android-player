package com.cube.lush.player.mobile.content;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cube.lush.player.R;
import com.cube.lush.player.api.model.MediaContent;
import com.cube.lush.player.api.model.RadioContent;
import com.cube.lush.player.api.model.VideoContent;
import com.cube.lush.player.content.handler.ResponseHandler;
import com.cube.lush.player.content.manager.MediaManager;
import com.cube.lush.player.content.model.CategoryContentType;
import com.cube.lush.player.content.util.MediaSorter;
import com.cube.lush.player.mobile.base.BaseContentFragment;

import java.util.ArrayList;
import java.util.List;

import uk.co.jamiecruwys.contracts.ListingData;

/**
 * Created by Jamie Cruwys of 3 SIDED CUBE on 18/04/2017.
 */
public class TagContentFragment extends BaseContentFragment
{
	@SuppressWarnings("HardCodedStringLiteral")
	private static final String ARG_TAG = "arg_tag";

	private String tag;

	public TagContentFragment()
	{
		// Required empty public constructor
	}

	public static TagContentFragment newInstance(@NonNull String tag)
	{
		TagContentFragment fragment = new TagContentFragment();
		Bundle args = new Bundle();
		args.putString(ARG_TAG, tag);
		fragment.setArguments(args);
		return fragment;
	}

	@Override public void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		tag = getArguments().getString(ARG_TAG, "");
	}

	@Override public void getListDataForFilterOption(@NonNull CategoryContentType contentType, @NonNull final ListingData callback)
	{
		if (contentType == CategoryContentType.ALL)
		{
			MediaManager.getInstance().getContentForTag(tag, 50, new ResponseHandler<MediaContent>()
			{
				@Override public void onSuccess(@NonNull List<MediaContent> items)
				{
					MediaSorter.MOST_RECENT_FIRST.sort(items);
					callback.onListingDataRetrieved(items);
				}

				@Override public void onFailure(@Nullable Throwable t)
				{
					callback.onListingDataError(t);
				}
			});
		}
		else if (contentType == CategoryContentType.TV)
		{
			MediaManager.getInstance().getVideos(new ResponseHandler<VideoContent>()
			{
				@Override public void onSuccess(@NonNull List<VideoContent> items)
				{
					ArrayList<MediaContent> mediaContent = new ArrayList<MediaContent>();
					mediaContent.addAll(items);

					List<MediaContent> filteredContent = MediaManager.getInstance().filterContentByTag(tag, mediaContent, 50);

					callback.onListingDataRetrieved(filteredContent);
				}

				@Override public void onFailure(@Nullable Throwable t)
				{
					callback.onListingDataError(t);
				}
			});
		}
		else if (contentType == CategoryContentType.RADIO)
		{
			MediaManager.getInstance().getRadios(new ResponseHandler<RadioContent>()
			{
				@Override public void onSuccess(@NonNull List<RadioContent> items)
				{
					ArrayList<MediaContent> mediaContent = new ArrayList<MediaContent>();
					mediaContent.addAll(items);

					List<MediaContent> filteredContent = MediaManager.getInstance().filterContentByTag(tag, mediaContent, 50);

					callback.onListingDataRetrieved(filteredContent);
				}

				@Override public void onFailure(@Nullable Throwable t)
				{
					callback.onListingDataError(t);
				}
			});
		}
	}

	@NonNull @Override public String provideContentTitle()
	{
		tag = tag.trim();

		String title = "Tag: ";

		if (tag.startsWith("#"))
		{
			return title + tag;
		}

		return title + "#" + tag;
	}

	@NonNull @Override public RecyclerView.LayoutManager provideLayoutManagerForFilterOption(CategoryContentType categoryContentType)
	{
		final int NUMBER_COLUMNS = getResources().getInteger(R.integer.tag_content_columns);
		return new GridLayoutManager(getContext(), NUMBER_COLUMNS);
	}

	@Override public void onSaveInstanceState(Bundle outState)
	{
		outState.putString(ARG_TAG, tag);
		super.onSaveInstanceState(outState);
	}
}