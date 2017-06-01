package com.cube.lush.player.mobile.search.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cube.lush.player.R;
import com.cube.lush.player.api.model.Programme;
import com.cube.lush.player.mobile.search.holder.SearchViewHolder;
import com.lush.lib.adapter.BaseSelectableListAdapter;
import com.lush.lib.listener.OnListItemClickListener;
import com.lush.view.holder.BaseViewHolder;

import java.util.List;

/**
 * Search Adapter
 *
 * @author Jamie Cruwys
 */
public class SearchAdapter extends BaseSelectableListAdapter<Programme>
{
	public SearchAdapter(List<Programme> items, OnListItemClickListener<Programme> listener)
	{
		super(items, listener);
	}

	@Override public BaseViewHolder<Programme> onCreateViewHolder(ViewGroup parent, int viewType)
	{
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
		return new SearchViewHolder(view);
	}
}