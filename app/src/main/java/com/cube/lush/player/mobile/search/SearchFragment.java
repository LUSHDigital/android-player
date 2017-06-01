package com.cube.lush.player.mobile.search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cube.lush.player.R;
import com.cube.lush.player.api.model.Programme;
import com.cube.lush.player.content.handler.ResponseHandler;
import com.cube.lush.player.content.repository.SearchProgrammeRepository;
import com.cube.lush.player.mobile.MainActivity;
import com.cube.lush.player.mobile.decoration.InsideSpacingItemDecoration;
import com.cube.lush.player.mobile.details.DetailsFragment;
import com.cube.lush.player.mobile.search.adapter.SearchAdapter;
import com.lush.lib.listener.OnListItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.jamiecruwys.StatefulListingFragment;
import uk.co.jamiecruwys.contracts.ListingData;

/**
 * Search Fragment
 *
 * @author Jamie Cruwys
 */
public class SearchFragment extends StatefulListingFragment<Programme> implements OnListItemClickListener<Programme>
{
	private static final String ARG_QUERY = "search_query";

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

	@NonNull @Override protected RecyclerView.Adapter provideAdapter(@NonNull List<Programme> items)
	{
		return new SearchAdapter(items, this);
	}

	@NonNull @Override protected RecyclerView.LayoutManager provideLayoutManager()
	{
		final int NUMBER_COLUMNS = getResources().getInteger(R.integer.search_columns);
		return new GridLayoutManager(getContext(), NUMBER_COLUMNS);
	}

	@Nullable @Override protected RecyclerView.ItemDecoration provideItemDecoration()
	{
		int spacing = (int)(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics()));
		final int NUMBER_COLUMNS = getResources().getInteger(R.integer.search_columns);
		return new InsideSpacingItemDecoration(spacing, 0, 0, 0, NUMBER_COLUMNS);
	}

	@Override protected void getListData(@NonNull final ListingData callback)
	{
		SearchProgrammeRepository.INSTANCE.setSearchTerm(query);
		SearchProgrammeRepository.INSTANCE.getItems(new ResponseHandler<Programme>()
		{
			@Override public void onSuccess(@NonNull List<Programme> items)
			{
				if (isAdded() && getActivity() != null)
				{
					callback.onListingDataRetrieved(items);
				}
			}

			@Override public void onFailure(@Nullable Throwable t)
			{
				if (isAdded() || getActivity() != null)
				{
					callback.onListingDataError(t);
				}
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
		populateUi();

		if (savedInstanceState != null)
		{
			String query = savedInstanceState.getString(ARG_QUERY, null);
			searchView.setQuery(query, true);
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

	@Override public void onItemClick(Programme searchResult, View view)
	{
		((MainActivity)getActivity()).showFragment(DetailsFragment.newInstance(searchResult));
	}

	@OnClick(R.id.search_again) void onSearchAgainClicked()
	{
		// Clear existing query and show the expanded state, ready for the user to input their new query
		searchView.setQuery("", false);
		searchView.onActionViewCollapsed();
		searchView.onActionViewExpanded();
	}

	@Override public void onSaveInstanceState(Bundle outState)
	{
		CharSequence query = searchView.getQuery();

		if (!TextUtils.isEmpty(query))
		{
			outState.putString(ARG_QUERY, String.valueOf(query));
		}

		super.onSaveInstanceState(outState);
	}
}