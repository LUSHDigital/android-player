package com.cube.lush.player.mobile.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.cube.lush.player.R;
import com.cube.lush.player.api.model.MediaContent;
import com.cube.lush.player.api.model.RadioContent;
import com.cube.lush.player.api.model.VideoContent;
import com.cube.lush.player.content.handler.ResponseHandler;
import com.cube.lush.player.content.manager.MediaManager;
import com.cube.lush.player.mobile.MainActivity;
import com.cube.lush.player.mobile.base.FilterableListingFragment;
import com.cube.lush.player.mobile.base.RecyclerViewClickedListener;
import com.cube.lush.player.mobile.content.adapter.ContentAdapter;
import com.cube.lush.player.mobile.details.DetailsFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uk.co.jamiecruwys.contracts.ListingData;

public class HomeFragment extends FilterableListingFragment<MediaContent, HomeTab> implements RecyclerViewClickedListener<MediaContent>
{
	public HomeFragment()
	{
		// Required empty public constructor
	}

	public static HomeFragment newInstance()
	{
		HomeFragment fragment = new HomeFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@NonNull @Override protected RecyclerView.Adapter provideAdapter(@NonNull List<MediaContent> items)
	{
		return new ContentAdapter(items, this);
	}

	@NonNull @Override public List<HomeTab> provideFilterOptions()
	{
		ArrayList<HomeTab> tabs= new ArrayList<HomeTab>();

		for (HomeTab tab : HomeTab.values())
		{
			tabs.add(tab);
		}

		return tabs;
	}

	@Override public void getListDataForFilterOption(@NonNull final HomeTab homeTab, @NonNull final ListingData callback)
	{
		callback.onListingDataRetrieved(Collections.EMPTY_LIST);

		MediaManager.getInstance().getVideos(new ResponseHandler<VideoContent>()
		{
			@Override public void onSuccess(@NonNull final List<VideoContent> videoItems)
			{
				MediaManager.getInstance().getRadios(new ResponseHandler<RadioContent>()
				{
					@Override public void onSuccess(@NonNull final List<RadioContent> radioItems)
					{
						ArrayList<MediaContent> items = new ArrayList<MediaContent>();
						items.addAll(videoItems);
						items.addAll(radioItems);

						List<MediaContent> results = filterContentByTag(items, homeTab.getTag());
						callback.onListingDataRetrieved(results);
					}

					@Override public void onFailure(@Nullable Throwable t)
					{
						callback.onListingDataError(t);
					}
				});
			}

			@Override public void onFailure(@Nullable Throwable t)
			{
				callback.onListingDataError(t);
			}
		});
	}

	private List<MediaContent> filterContentByTag(@NonNull ArrayList<MediaContent> items, @Nullable String tag)
	{
		if (TextUtils.isEmpty(tag))
		{
			return items;
		}

		ArrayList<MediaContent> results = new ArrayList<MediaContent>();

		for (MediaContent item : items)
		{
			if (item.getTags() != null && item.getTags().contains(tag))
			{
				results.add(item);
			}
		}

		return results;
	}

	@NonNull @Override public String getTitleForFilterOption(HomeTab homeTab)
	{
		return homeTab.getDisplayName();
	}

	@NonNull @Override public HomeTab provideDefaultTab()
	{
		return HomeTab.ALL;
	}

	@Override public int provideLoadingLayout()
	{
		return R.layout.home_loading;
	}

	@Override public int provideEmptyLayout()
	{
		return R.layout.home_empty;
	}

	@Override public int provideErrorLayout()
	{
		return R.layout.home_error;
	}

	@Override public void onRecyclerViewItemClicked(@NonNull MediaContent item)
	{
		((MainActivity)getActivity()).showFragment(DetailsFragment.newInstance(item));
	}
}