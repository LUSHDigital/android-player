package com.cube.lush.player.mobile.events;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cube.lush.player.R;
import com.cube.lush.player.api.model.MediaContent;
import com.cube.lush.player.mobile.base.BaseAdapter;
import com.cube.lush.player.mobile.base.ListDataRetrieval;
import com.cube.lush.player.mobile.base.ListingFragment;
import com.cube.lush.player.mobile.base.RecyclerViewClickedListener;
import com.cube.lush.player.mobile.events.adapter.EventsAdapter;

import java.util.Collections;

public class EventsFragment extends ListingFragment implements RecyclerViewClickedListener<MediaContent>
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

	@NonNull @Override protected RecyclerView.LayoutManager provideLayoutManager()
	{
		return new LinearLayoutManager(getContext());
	}

	@NonNull @Override protected BaseAdapter provideAdapter()
	{
		return new EventsAdapter(Collections.EMPTY_LIST, this);
	}

	@Override protected void getListData(@NonNull ListDataRetrieval callback)
	{
		callback.onListDataRetrieved(Collections.EMPTY_LIST);
	}

	@Override public void onRecyclerViewItemClicked(@NonNull MediaContent item)
	{
		// TODO:
	}
}