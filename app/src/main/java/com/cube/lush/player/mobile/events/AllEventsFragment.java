package com.cube.lush.player.mobile.events;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.cube.lush.player.R;
import com.cube.lush.player.api.model.MediaContent;
import com.cube.lush.player.content.handler.ResponseHandler;
import com.cube.lush.player.content.manager.MediaManager;
import com.cube.lush.player.mobile.MainActivity;
import com.cube.lush.player.mobile.base.EventTabSelection;
import com.cube.lush.player.mobile.base.FilterableListingFragment;
import com.cube.lush.player.mobile.base.RecyclerViewClickedListener;
import com.cube.lush.player.mobile.details.DetailsFragment;
import com.cube.lush.player.mobile.events.adapter.AllEventsAdapter;

import java.util.ArrayList;
import java.util.List;

import uk.co.jamiecruwys.contracts.ListingData;

/**
 * Created by Jamie Cruwys of 3 SIDED CUBE on 13/04/2017.
 */
public class AllEventsFragment extends FilterableListingFragment<MediaContent, EventTab> implements RecyclerViewClickedListener<MediaContent>,EventTabSelection
{
	public AllEventsFragment()
	{
		// Required empty public constructor
	}

	public static AllEventsFragment newInstance()
	{
		AllEventsFragment fragment = new AllEventsFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@NonNull @Override protected RecyclerView.Adapter provideAdapter(@NonNull List<MediaContent> items)
	{
		ArrayList<EventTab> tabs = new ArrayList<EventTab>();

		for (EventTab tab : EventTab.values())
		{
			tabs.add(tab);
		}

		return new AllEventsAdapter(getContext(), tabs, items, this, this);
	}

	@NonNull @Override public List<EventTab> provideFilterOptions()
	{
		ArrayList<EventTab> tabs = new ArrayList<EventTab>();

		for (EventTab tab : EventTab.values())
		{
			tabs.add(tab);
		}

		return tabs;
	}

	@Override public void getListDataForFilterOption(@NonNull EventTab eventTab, @NonNull final ListingData callback)
	{
		MediaManager.getInstance().getAllContent(new ResponseHandler<MediaContent>()
		{
			@Override public void onSuccess(@NonNull List<MediaContent> items)
			{
				callback.onListingDataRetrieved(items);
			}

			@Override public void onFailure(@Nullable Throwable t)
			{
				callback.onListingDataError(t);
			}
		});
	}

	@NonNull @Override public String getTitleForFilterOption(EventTab eventTab)
	{
		return eventTab.getDisplayName();
	}

	@NonNull @Override public EventTab provideDefaultTab()
	{
		return EventTab.ALL;
	}

	@Override public int provideLoadingLayout()
	{
		return R.layout.event_loading;
	}

	@Override public int provideEmptyLayout()
	{
		return R.layout.event_empty;
	}

	@Override public int provideErrorLayout()
	{
		return R.layout.event_error;
	}

	@Override public void onRecyclerViewItemClicked(@NonNull MediaContent item)
	{
		((MainActivity)getActivity()).showFragment(DetailsFragment.newInstance(item));
	}

	@Override public void selectTab(@NonNull EventTab tab)
	{
		selectOption(tab);
		Toast.makeText(getContext(), "Should select tab: " + tab.getDisplayName(), Toast.LENGTH_SHORT).show();
	}
}