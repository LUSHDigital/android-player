package com.cube.lush.player.mobile.channels;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.widget.Toast;

import com.cube.lush.player.content.model.Channel;
import com.cube.lush.player.R;
import com.cube.lush.player.mobile.MainActivity;
import com.cube.lush.player.mobile.base.BaseAdapter;
import com.cube.lush.player.mobile.base.ListDataRetrieval;
import com.cube.lush.player.mobile.base.ListingFragment;
import com.cube.lush.player.mobile.base.RecyclerViewClickedListener;
import com.cube.lush.player.mobile.channels.adapter.ChannelsAdapter;
import com.cube.lush.player.mobile.decorators.GridSpacingDecoration;
import com.cube.lush.player.mobile.content.ContentFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChannelsFragment extends ListingFragment implements RecyclerViewClickedListener<Channel>
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

	@NonNull @Override protected BaseAdapter provideAdapter()
	{
		return new ChannelsAdapter(new ArrayList<Channel>(), this);
	}

	@Nullable @Override protected RecyclerView.ItemDecoration provideItemDecoration()
	{
		final int NUMBER_COLUMNS = getResources().getInteger(R.integer.channel_columns);
		int spacing = (int)(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics()));
		return new GridSpacingDecoration(spacing, NUMBER_COLUMNS);
	}

	@Override protected void getListData(@NonNull ListDataRetrieval callback)
	{
		List<Channel> channels = Arrays.asList(Channel.values());
		callback.onListDataRetrieved(channels);
	}

	@Override public void onRecyclerViewItemClicked(@NonNull Channel channel)
	{
		Toast.makeText(getContext(), getString(R.string.channel_selected, channel.getTitle()), Toast.LENGTH_SHORT).show();
		((MainActivity)getActivity()).showFragment(ContentFragment.newInstance(channel));
	}
}