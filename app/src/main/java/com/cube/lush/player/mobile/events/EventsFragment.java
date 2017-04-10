package com.cube.lush.player.mobile.events;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.cube.lush.player.R;
import com.cube.lush.player.api.model.MediaContent;
import com.cube.lush.player.mobile.base.RecyclerViewClickedListener;
import com.cube.lush.player.mobile.events.adapter.EventsAdapter;

import java.util.Collections;
import java.util.List;

import uk.co.jamiecruwys.StatefulListingFragment;
import uk.co.jamiecruwys.contracts.ListingData;

public class EventsFragment extends StatefulListingFragment<MediaContent> implements RecyclerViewClickedListener<MediaContent>
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

	@NonNull @Override protected RecyclerView.Adapter provideAdapter(@NonNull List<MediaContent> items)
	{
		return new EventsAdapter(items, this);
	}

	@Override protected void getListData(@NonNull ListingData callback)
	{
		callback.onListingDataRetrieved(Collections.EMPTY_LIST);
	}

	@Override public int provideLoadingLayout()
	{
		return R.layout.mobile_loading;
	}

	@Override public int provideEmptyLayout()
	{
		return R.layout.mobile_empty;
	}

	@Override public int provideErrorLayout()
	{
		return R.layout.mobile_error;
	}

	@Override public void onRecyclerViewItemClicked(@NonNull MediaContent item)
	{
		// TODO:
	}
}