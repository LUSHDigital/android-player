package com.cube.lush.player.mobile.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cube.lush.player.R;
import com.cube.lush.player.api.model.MediaContent;
import com.cube.lush.player.content.handler.ResponseHandler;
import com.cube.lush.player.content.manager.MediaManager;
import com.cube.lush.player.content.model.CategoryContentType;
import com.cube.lush.player.content.model.Channel;
import com.cube.lush.player.content.util.MediaSorter;
import com.cube.lush.player.mobile.MainActivity;
import com.cube.lush.player.mobile.content.adapter.ContentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jamie Cruwys of 3 SIDED CUBE on 04/04/2017.
 */
public abstract class ListingFragment extends Fragment implements ListDataRetrieval
{
	/**
	 * Provide the layout manager
	 * @return
	 */
	@NonNull protected abstract RecyclerView.LayoutManager provideLayoutManager();

	/**
	 * Provide the adapter
	 * @return
	 */
	@NonNull protected abstract BaseAdapter provideAdapter();

	/**
	 * Get the data that will be displayed in the list
	 */
	protected abstract void getListData(@NonNull ListDataRetrieval callback);

	@BindView(R.id.recycler) RecyclerView recycler;
	private RecyclerView.LayoutManager layoutManager;
	private BaseAdapter adapter;

	@Override public void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.layoutManager = provideLayoutManager();
		this.adapter = provideAdapter();
	}

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.mobile_fragment_listing, container, false);
		ButterKnife.bind(this, view);
		return view;
	}

	@Override public void onActivityCreated(@Nullable Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		if (savedInstanceState == null)
		{
			recycler.setLayoutManager(provideLayoutManager());
			recycler.setAdapter(adapter);
		}
	}

	@Override public void onResume()
	{
		super.onResume();

		showLoading();
		getListData(this);
	}

	@Override public void onListDataRetrieved(@NonNull List<MediaContent> items)
	{
		adapter.setItems(items);

		hideLoading();

		if (items.size() <= 0)
		{
			// TODO: Show empty state
		}
		else
		{
			// TODO: Show content
		}
	}

	@Override public void onListDataRetrievalError(@Nullable Throwable throwable)
	{
	 	adapter.setItems(null);

		// TODO: Show erorr state
	}

	private void showLoading()
	{
		final MainActivity mainActivity = ((MainActivity)getActivity());
		mainActivity.showLoading();
	}

	private void hideLoading()
	{
		final MainActivity mainActivity = ((MainActivity)getActivity());
		mainActivity.hideLoading();
	}
}