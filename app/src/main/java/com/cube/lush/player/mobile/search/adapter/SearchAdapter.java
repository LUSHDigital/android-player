package com.cube.lush.player.mobile.search.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cube.lush.player.R;
import com.cube.lush.player.api.model.MediaContent;
import com.cube.lush.player.api.model.SearchResult;
import com.cube.lush.player.mobile.search.holder.SearchViewHolder;
import com.cube.lush.player.mobile.search.listener.SearchResultClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;

/**
 * Created by Jamie Cruwys of 3 SIDED CUBE on 04/04/2017.
 */
@AllArgsConstructor
public class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder>
{
	private @NonNull ArrayList<SearchResult> searchResults = new ArrayList<SearchResult>();
	private @NonNull SearchResultClickListener searchResultClickListener = null;

	@Override public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.mobile_item_search, parent, false);
		return new SearchViewHolder(itemView, searchResultClickListener);
	}

	@Override public void onBindViewHolder(SearchViewHolder holder, int position)
	{
		SearchResult searchResult = searchResults.get(position);
		holder.setSearchResult(searchResult);

		holder.type.setText(searchResult.getType().getName());
		holder.title.setText(searchResult.getTitle());

		Picasso.with(holder.image.getContext())
			.load(searchResult.getThumbnail())
			.fit()
			.centerInside()
			.into(holder.image);
	}

	@Override public int getItemCount()
	{
		return searchResults.size();
	}

	public void setItems(List<SearchResult> newSearchResults)
	{
		searchResults.clear();

		if (newSearchResults != null)
		{
			searchResults.addAll(newSearchResults);
		}

		notifyDataSetChanged();
	}
}