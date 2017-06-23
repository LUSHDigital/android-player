package com.cube.lush.player.mobile.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cube.lush.player.R;
import com.lush.player.api.model.Programme;
import com.cube.lush.player.content.handler.ResponseHandler;
import com.cube.lush.player.content.repository.LatestProgrammesRepository;
import com.cube.lush.player.content.util.MediaSorter;
import com.cube.lush.player.mobile.MainActivity;
import com.cube.lush.player.mobile.content.adapter.ContentAdapter;
import com.cube.lush.player.mobile.details.DetailsFragment;
import com.lush.lib.listener.OnListItemClickListener;

import java.util.List;

import uk.co.jamiecruwys.StatefulListingFragment;
import uk.co.jamiecruwys.contracts.ListingData;

/**
 * Home Fragment
 *
 * @author Jamie Cruwys
 */
public class HomeFragment extends StatefulListingFragment<Programme> implements OnListItemClickListener<Programme>
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

	@NonNull @Override protected RecyclerView.Adapter provideAdapter(@NonNull List<Programme> items)
	{
		return new ContentAdapter(items, this);
	}

	@Override protected void getListData(@NonNull final ListingData callback)
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

	@Override public void onItemClick(Programme programme, View view)
	{
		((MainActivity)getActivity()).showFragment(DetailsFragment.newInstance(programme));
	}
}