package com.cube.lush.player.mobile.search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cube.lush.player.R;
import com.cube.lush.player.api.model.SearchResult;
import com.cube.lush.player.content.handler.ResponseHandler;
import com.cube.lush.player.content.manager.SearchManager;
import com.cube.lush.player.mobile.MainActivity;
import com.cube.lush.player.mobile.base.RecyclerViewClickedListener;
import com.cube.lush.player.mobile.decorators.TopSpacingDecoration;
import com.cube.lush.player.mobile.details.DetailsFragment;
import com.cube.lush.player.mobile.search.adapter.SearchAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.jamiecruwys.StatefulListingFragment;
import uk.co.jamiecruwys.contracts.ListingData;

public class SearchFragment extends StatefulListingFragment<SearchResult> implements RecyclerViewClickedListener<SearchResult>
{
	@BindView(R.id.search) SearchView searchView;
	public static String query = "";

	public SearchFragment()
	{
		// Required empty public constructor
	}

	public static SearchFragment newInstance()
	{
		SearchFragment fragment = new SearchFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = super.onCreateView(inflater, container, savedInstanceState);
		ButterKnife.bind(this, view);

		return view;
	}

	@NonNull @Override protected RecyclerView.Adapter provideAdapter(@NonNull List<SearchResult> items)
	{
		return new SearchAdapter(items, this);
	}

	@Nullable @Override protected RecyclerView.ItemDecoration provideItemDecoration()
	{
		int spacing = (int)(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics()));
		return new TopSpacingDecoration(spacing);
	}

	@Override protected void getListData(@NonNull final ListingData callback)
	{
		if (TextUtils.isEmpty(query))
		{
			return;
		}

		SearchManager.getInstance().search(query, new ResponseHandler<SearchResult>()
		{
			@Override public void onSuccess(@NonNull List<SearchResult> items)
			{
				callback.onListingDataRetrieved(items);
			}

			@Override public void onFailure(@Nullable Throwable t)
			{
				callback.onListingDataError(t);
			}
		});
	}

	@Override public int provideLayout()
	{
		return R.layout.search_layout;
	}

	@Override public int provideStatefulViewId()
	{
		return R.id.statefulview;
	}

	@Override public int provideLoadedLayout()
	{
		return R.layout.search_loaded;
	}

	@Override public int provideLoadingLayout()
	{
		return R.layout.search_loading;
	}

	@Override public int provideEmptyLayout()
	{
		return R.layout.search_empty;
	}

	@Override public int provideErrorLayout()
	{
		return R.layout.search_error;
	}

	@Override protected boolean shouldReloadOnResume()
	{
		return false;
	}

	@Override public void onActivityCreated(@Nullable Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		if (savedInstanceState == null)
		{
			populateUi();
		}
	}

	private void populateUi()
	{
		// Make it default to the expanded state
		searchView.onActionViewExpanded();

		final ListingData callback = this;

		// Set the listener to get data on submit
		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
		{
			@Override public boolean onQueryTextSubmit(String query)
			{
				SearchFragment.query = query;
				getListData(callback);
				return true;
			}

			@Override public boolean onQueryTextChange(String newText)
			{
				return false;
			}
		});
	}

	@Override public void onRecyclerViewItemClicked(@NonNull SearchResult item)
	{
		((MainActivity)getActivity()).showFragment(DetailsFragment.newInstance(item));
	}
}