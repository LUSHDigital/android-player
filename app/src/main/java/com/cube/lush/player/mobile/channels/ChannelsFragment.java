package com.cube.lush.player.mobile.channels;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;

import com.cube.lush.player.content.model.Channel;
import com.cube.lush.player.R;
import com.cube.lush.player.mobile.MainActivity;
import com.cube.lush.player.mobile.base.RecyclerViewClickedListener;
import com.cube.lush.player.mobile.channels.adapter.ChannelsAdapter;
import com.cube.lush.player.mobile.decorators.GridSpacingDecoration;
import com.cube.lush.player.mobile.content.ContentFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uk.co.jamiecruwys.StatefulListingFragment;
import uk.co.jamiecruwys.contracts.ListingData;

public class ChannelsFragment extends StatefulListingFragment<Channel> implements RecyclerViewClickedListener<Channel>
{
	public ChannelsFragment()
	{
		// Required empty public constructor
	}

	public static ChannelsFragment newInstance()
	{
		ChannelsFragment fragment = new ChannelsFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@NonNull @Override protected RecyclerView.LayoutManager provideLayoutManager()
	{
		final int NUMBER_COLUMNS = getResources().getInteger(R.integer.channel_columns);
		return new GridLayoutManager(getContext(), NUMBER_COLUMNS);
	}

	@NonNull @Override protected RecyclerView.Adapter provideAdapter(@NonNull List<Channel> items)
	{
		return new ChannelsAdapter(items, this);
	}

	@Nullable @Override protected RecyclerView.ItemDecoration provideItemDecoration()
	{
		final int NUMBER_COLUMNS = getResources().getInteger(R.integer.channel_columns);
		int spacing = (int)(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics()));
		return new GridSpacingDecoration(spacing, NUMBER_COLUMNS);
	}

	@Override protected void getListData(@NonNull ListingData listingData)
	{
		List<Channel> channels = Arrays.asList(Channel.values());
		listingData.onListingDataRetrieved(channels);
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

	@Override public void onRecyclerViewItemClicked(@NonNull Channel item)
	{
		((MainActivity)getActivity()).showFragment(ContentFragment.newInstance(item));
	}
}