package com.cube.lush.player.mobile.search.adapter;

import android.support.annotation.NonNull;
import android.view.View;

import com.cube.lush.player.R;
import com.cube.lush.player.api.model.SearchResult;
import com.cube.lush.player.mobile.base.BaseAdapter;
import com.cube.lush.player.mobile.search.holder.SearchViewHolder;
import com.cube.lush.player.mobile.search.listener.SearchResultClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Jamie Cruwys of 3 SIDED CUBE on 04/04/2017.
 */
public class SearchAdapter extends BaseAdapter<SearchResult, SearchViewHolder>
{
	private SearchResultClickListener listener;

	public SearchAdapter(@NonNull List<SearchResult> items, @NonNull SearchResultClickListener listener)
	{
		super(items);
		this.listener = listener;
	}

	@Override protected int provideViewHolderLayout()
	{
		return R.layout.mobile_item_search;
	}

	@NonNull @Override protected SearchViewHolder createViewHolder(@NonNull View itemView)
	{
		return new SearchViewHolder(itemView, listener);
	}

	@Override protected void bind(@NonNull SearchViewHolder holder, @NonNull SearchResult item)
	{
		holder.setSearchResult(item);

		holder.type.setText(item.getType().getName());
		holder.title.setText(item.getTitle());

		Picasso.with(holder.image.getContext())
			.load(item.getThumbnail())
			.fit()
			.centerInside()
			.into(holder.image);
	}
}