package com.cube.lush.player.mobile.live;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.cube.lush.player.R;
import com.cube.lush.player.api.model.MediaContent;
import com.cube.lush.player.content.handler.ResponseHandler;
import com.cube.lush.player.content.manager.MediaManager;
import com.cube.lush.player.mobile.base.BaseAdapter;
import com.cube.lush.player.mobile.base.ListDataRetrieval;
import com.cube.lush.player.mobile.base.ListingFragment;
import com.cube.lush.player.mobile.base.RecyclerViewClickedListener;
import com.cube.lush.player.mobile.content.adapter.ContentAdapter;

import java.util.Collections;
import java.util.List;

public class LiveFragment extends ListingFragment implements RecyclerViewClickedListener<MediaContent>
{
	public LiveFragment()
	{
		// Required empty public constructor
	}

	public static LiveFragment newInstance()
	{
		LiveFragment fragment = new LiveFragment();
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
		return new ContentAdapter(Collections.EMPTY_LIST, this);
	}

	// TODO: Set adapter to use the following layout:
	// return R.layout.mobile_fragment_live;

	@Override protected void getListData(@NonNull final ListDataRetrieval callback)
	{
		MediaManager.getInstance().getLiveContent(new ResponseHandler<MediaContent>()
		{
			@Override public void onSuccess(@NonNull List<MediaContent> items)
			{
				callback.onListDataRetrieved(items);
			}

			@Override public void onFailure(@Nullable Throwable t)
			{
				callback.onListDataRetrievalError(t);
			}
		});
	}

	@Override public void onRecyclerViewItemClicked(@NonNull MediaContent item)
	{
		Toast.makeText(getContext(), "Live item clicked: " + item.getTitle(), Toast.LENGTH_SHORT).show();
	}
}