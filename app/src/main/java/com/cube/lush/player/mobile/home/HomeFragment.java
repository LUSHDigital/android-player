package com.cube.lush.player.mobile.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cube.lush.player.R;
import com.cube.lush.player.api.model.MediaContent;
import com.cube.lush.player.content.handler.ResponseHandler;
import com.cube.lush.player.content.manager.MediaManager;
import com.cube.lush.player.mobile.MainActivity;
import com.cube.lush.player.mobile.content.adapter.ContentAdapter;
import com.cube.lush.player.mobile.details.DetailsFragment;
import com.lush.lib.listener.OnListItemClickListener;

import java.util.List;

import uk.co.jamiecruwys.StatefulListingFragment;
import uk.co.jamiecruwys.contracts.ListingData;

/**
 * Created by Jamie Cruwys.
 */
public class HomeFragment extends StatefulListingFragment<MediaContent> implements OnListItemClickListener<MediaContent>
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

	@NonNull @Override protected RecyclerView.LayoutManager provideLayoutManager()
	{
		final int NUMBER_COLUMNS = getResources().getInteger(R.integer.home_columns);
		return new GridLayoutManager(getContext(), NUMBER_COLUMNS);
	}

	@NonNull @Override protected RecyclerView.Adapter provideAdapter(@NonNull List<MediaContent> items)
	{
		return new ContentAdapter(items, this);
	}

	@Override protected void getListData(@NonNull final ListingData callback)
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

	@Override public void onItemClick(MediaContent mediaContent, View view)
	{
		((MainActivity)getActivity()).showFragment(DetailsFragment.newInstance(mediaContent));
	}
}