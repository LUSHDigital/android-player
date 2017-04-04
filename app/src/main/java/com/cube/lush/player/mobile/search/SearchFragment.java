package com.cube.lush.player.mobile.search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cube.lush.player.R;
import com.cube.lush.player.api.model.SearchResult;
import com.cube.lush.player.content.handler.ResponseHandler;
import com.cube.lush.player.content.manager.SearchManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchFragment extends Fragment
{
	@BindView(R.id.search) SearchView searchView;

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
		View view = inflater.inflate(R.layout.mobile_fragment_search, container, false);
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

		// Set the listener to get data on submit
		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
		{
			@Override public boolean onQueryTextSubmit(String query)
			{
				SearchManager.getInstance().search(query, new ResponseHandler<SearchResult>()
				{
					@Override public void onSuccess(@NonNull List<SearchResult> items)
					{
						// TODO: Implement success
					}

					@Override public void onFailure(@Nullable Throwable t)
					{
						// TODO: Implement failure
					}
				});

				return true;
			}

			@Override public boolean onQueryTextChange(String newText)
			{
				return false;
			}
		});
	}
}