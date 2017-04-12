package com.cube.lush.player.mobile.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.cube.lush.player.R;
import com.cube.lush.player.api.model.MediaContent;
import com.cube.lush.player.mobile.base.RecyclerViewClickedListener;
import com.cube.lush.player.mobile.content.adapter.ContentAdapter;

import java.util.Collections;
import java.util.List;

import uk.co.jamiecruwys.StatefulListingFragment;
import uk.co.jamiecruwys.contracts.ListingData;

public class HomeFragment extends StatefulListingFragment<MediaContent> implements RecyclerViewClickedListener<MediaContent>
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

	@Override protected void getListData(@NonNull ListingData callback)
	{
		callback.onListingDataRetrieved(Collections.EMPTY_LIST);
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
		// TODO:
	}
}