package com.cube.lush.player.mobile.events;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.cube.lush.player.R;
import com.lush.player.api.model.Programme;
import com.cube.lush.player.content.handler.ResponseHandler;
import com.cube.lush.player.content.repository.EventProgrammesRepository;
import com.cube.lush.player.content.repository.LatestProgrammesRepository;
import com.cube.lush.player.content.util.MediaSorter;
import com.cube.lush.player.mobile.MainActivity;
import com.cube.lush.player.mobile.base.FilterableListingFragment;
import com.cube.lush.player.mobile.content.adapter.ContentAdapter;
import com.cube.lush.player.mobile.decoration.InsideSpacingItemDecoration;
import com.cube.lush.player.mobile.details.DetailsFragment;
import com.cube.lush.player.mobile.events.adapter.EventsAdapter;
import com.lush.lib.listener.OnListItemClickListener;

import java.util.List;

import uk.co.jamiecruwys.contracts.ListingData;

/**
 * Events Fragment
 *
 * @author Jamie Cruwys
 */
public class EventsFragment extends FilterableListingFragment<Programme, EventTab> implements OnListItemClickListener<Programme>, EventTabSelection
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
		if (eventTab == EventTab.ALL)
		{
			LatestProgrammesRepository.INSTANCE.getItems(new ResponseHandler<Programme>()
			{
				@Override public void onSuccess(@NonNull List<Programme> items)
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
		else
		{
			EventProgrammesRepository.INSTANCE.setEventTag(eventTab.getTag());
			EventProgrammesRepository.INSTANCE.getItems(new ResponseHandler<Programme>()
			{
				@Override public void onSuccess(@NonNull List<Programme> items)
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
	}

	@NonNull @Override public String getTitleForFilterOption(EventTab eventTab)
	{
		return eventTab.getDisplayName();
	}

	@NonNull @Override public EventTab provideDefaultTab()
	{
		return EventTab.ALL;
	}

	@NonNull @Override public LinearLayoutManager provideLayoutManagerForFilterOption(EventTab eventTab)
	{
		final int NUMBER_COLUMNS;

		if (eventTab == EventTab.ALL)
		{
			NUMBER_COLUMNS = getResources().getInteger(R.integer.paging_columns);
		}
		else
		{
			NUMBER_COLUMNS = getResources().getInteger(R.integer.events_columns);
		}

		return new GridLayoutManager(getContext(), NUMBER_COLUMNS);
	}

	@NonNull @Override public RecyclerView.Adapter provideAdapterForFilterOption(EventTab eventTab, @NonNull List<Programme> items)
	{
		if (eventTab == EventTab.ALL)
		{
			return new EventsAdapter(items, this, this);
		}
		else
		{
			return new ContentAdapter(items, this);
		}
	}

	@Nullable @Override public RecyclerView.ItemDecoration provideItemDecorationForFilterOption(EventTab eventTab)
	{
		// TODO: Remove top spacing on phones/tablet
		if (eventTab == EventTab.ALL)
		{
			int spacing = (int)(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getContext().getResources().getDisplayMetrics()));
			final int NUMBER_COLUMNS = getResources().getInteger(R.integer.paging_columns);

			return new InsideSpacingItemDecoration(spacing, 0 ,0 ,0, NUMBER_COLUMNS);
		}

		return null;
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

	@Override public void onItemClick(Programme item, View view)
	{
		((MainActivity)getActivity()).showFragment(DetailsFragment.newInstance(item));
	}

	@Override public void selectTab(@NonNull EventTab tab)
	{
		selectOption(tab);
	}
}