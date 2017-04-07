package com.cube.lush.player.mobile.search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cube.lush.player.R;
import com.cube.lush.player.api.model.SearchResult;
import com.cube.lush.player.content.handler.ResponseHandler;
import com.cube.lush.player.content.manager.SearchManager;
import com.cube.lush.player.mobile.MainActivity;
import com.cube.lush.player.mobile.base.BaseAdapter;
import com.cube.lush.player.mobile.base.ListDataRetrieval;
import com.cube.lush.player.mobile.base.ListingFragment;
import com.cube.lush.player.mobile.base.RecyclerViewClickedListener;
import com.cube.lush.player.mobile.details.DetailsFragment;
import com.cube.lush.player.mobile.search.adapter.SearchAdapter;
import com.cube.lush.player.mobile.decorators.TopSpacingDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.jamiecruwys.State;

public class SearchFragment extends ListingFragment implements RecyclerViewClickedListener<SearchResult>
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

	@NonNull @Override protected RecyclerView.LayoutManager provideLayoutManager()
	{
		return new LinearLayoutManager(getContext());
	}

	@NonNull @Override protected BaseAdapter provideAdapter()
	{
		return new SearchAdapter(new ArrayList<SearchResult>(), this);
	}

	@Nullable @Override protected RecyclerView.ItemDecoration provideItemDecoration()
	{
		int spacing = (int)(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics()));
		return new TopSpacingDecoration(spacing);
	}

	@Override protected void getListData(@NonNull ListDataRetrieval callback)
	{
		if (TextUtils.isEmpty(query))
		{
			return;
		}

		SearchManager.getInstance().search(query, new ResponseHandler<SearchResult>()
		{
			@Override public void onSuccess(@NonNull List<SearchResult> items)
			{
				adapter.setItems(items);
				setState(State.SHOWING_CONTENT);
			}

			@Override public void onFailure(@Nullable Throwable t)
			{
				adapter.setItems(null);
				setState(State.ERROR);
			}
		});
	}

	@Override protected boolean shouldReloadOnResume()
	{
		return false;
	}

	@Override protected int provideContentLayout()
	{
		return R.layout.mobile_fragment_search_content;
	}

	@Override protected int provideEmptyLayout()
	{
		return R.layout.mobile_empty;
	}

	@Override protected int provideLoadingLayout()
	{
		return R.layout.mobile_loading;
	}

	@Override protected int provideErrorLayout()
	{
		return R.layout.mobile_error;
	}

	@Override protected int provideLayout()
	{
		return R.layout.mobile_fragment_search;
	}

	@Override protected int provideStatefulViewId()
	{
		return R.id.statefulview;
	}

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = super.onCreateView(inflater, container, savedInstanceState);
		ButterKnife.bind(this, view);

		return view;
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

		final ListDataRetrieval callback = this;

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

	@Override public void onRecyclerViewItemClicked(@NonNull SearchResult searchResult)
	{
		Toast.makeText(searchView.getContext(), "Search result clicked: " + searchResult.getTitle(), Toast.LENGTH_SHORT).show();
		((MainActivity)getActivity()).showFragment(DetailsFragment.newInstance(searchResult));
	}
}