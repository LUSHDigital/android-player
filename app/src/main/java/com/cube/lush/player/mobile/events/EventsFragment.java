package com.cube.lush.player.mobile.events;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cube.lush.player.R;
import com.cube.lush.player.api.model.MediaContent;
import com.cube.lush.player.content.handler.ResponseHandler;
import com.cube.lush.player.content.manager.MediaManager;
import com.cube.lush.player.mobile.MainActivity;
import com.cube.lush.player.mobile.base.FilterableListingFragment;
import com.cube.lush.player.mobile.details.DetailsFragment;
import com.cube.lush.player.mobile.events.adapter.EventsAdapter;
import com.lush.lib.listener.OnListItemClickListener;

import java.util.List;

import uk.co.jamiecruwys.contracts.ListingData;

/**
 * Created by Jamie Cruwys.
 */
public class EventsFragment extends FilterableListingFragment<MediaContent, EventTab> implements OnListItemClickListener<MediaContent>, EventTabSelection
{
	public EventsFragment()
	{
		// Required empty public constructor
	}

	public static EventsFragment newInstance()
	{
		EventsFragment fragment = new EventsFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@NonNull @Override public List<EventTab> provideFilterOptions()
	{
		return EventTab.listValues();
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

	@NonNull @Override protected RecyclerView.Adapter provideAdapter(@NonNull List<MediaContent> items)
	{
		return new EventsAdapter(items, this, this);
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

	@Override public void onItemClick(MediaContent item, View view)
	{
		((MainActivity)getActivity()).showFragment(DetailsFragment.newInstance(item));
	}

	@Override public void selectTab(@NonNull EventTab tab)
	{
		selectOption(tab);
	}
}