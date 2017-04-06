package com.cube.lush.player.mobile.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cube.lush.player.R;
import com.cube.lush.player.api.model.MediaContent;
import com.cube.lush.player.mobile.MainActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.jamiecruwys.State;
import uk.co.jamiecruwys.StatefulFragment;

/**
 * Created by Jamie Cruwys of 3 SIDED CUBE on 04/04/2017.
 */
public abstract class ListingFragment extends StatefulFragment implements ListDataRetrieval
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

	@Nullable protected RecyclerView.ItemDecoration provideItemDecoration()
	{
		return null;
	}

	/**
	 * Get the data that will be displayed in the list
	 */
	protected abstract void getListData(@NonNull ListDataRetrieval callback);

	/**
	 * Provide the layouts for each state this view can be in
	 */

	@Override protected int provideContentLayout()
	{
		return R.layout.mobile_listing;
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
		View view = super.onCreateView(inflater, container, savedInstanceState);
		ButterKnife.bind(this, view);
		return view;
	}

	@Override public void onActivityCreated(@Nullable Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		if (savedInstanceState == null)
		{
			recycler.setLayoutManager(layoutManager);
			recycler.setAdapter(adapter);

			RecyclerView.ItemDecoration itemDecoration = provideItemDecoration();
			if (itemDecoration != null)
			{
				recycler.addItemDecoration(itemDecoration);
			}
		}
	}

	@Override public void onResume()
	{
		super.onResume();
		setState(State.LOADING);
		getListData(this);
	}

	@Override public void onListDataRetrieved(@NonNull List<?> items)
	{
		adapter.setItems(items);

		if (items.size() <= 0)
		{
			setState(State.EMPTY);
		}
		else
		{
			setState(State.SHOWING_CONTENT);
		}
	}

	@Override public void onListDataRetrievalError(@Nullable Throwable throwable)
	{
		adapter.clearItems();
		setState(State.ERROR);
	}
}