package com.cube.lush.player.mobile.home;

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
import com.cube.lush.player.mobile.content.adapter.ContentAdapter;
import com.cube.lush.player.mobile.details.DetailsFragment;
import com.lush.lib.listener.OnListItemClickListener;

import java.util.ArrayList;
import java.util.List;

import uk.co.jamiecruwys.contracts.ListingData;

public class HomeFragment extends FilterableListingFragment<MediaContent, HomeTab> implements OnListItemClickListener<MediaContent>
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

	@NonNull @Override public List<HomeTab> provideFilterOptions()
	{
		ArrayList<HomeTab> tabs= new ArrayList<HomeTab>();

		for (HomeTab tab : HomeTab.values())
		{
			tabs.add(tab);
		}

		return tabs;
	}

	@Override public void getListDataForFilterOption(@NonNull final HomeTab homeTab, @NonNull final ListingData callback)
	{
		MediaManager.getInstance().getContentForTag(homeTab.getTag(), new ResponseHandler<MediaContent>()
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

	@NonNull @Override public String getTitleForFilterOption(HomeTab homeTab)
	{
		return homeTab.getDisplayName();
	}

	@NonNull @Override public HomeTab provideDefaultTab()
	{
		return HomeTab.ALL;
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