package com.cube.lush.player.mobile.search.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cube.lush.player.R;
import com.cube.lush.player.api.model.SearchResult;
import com.cube.lush.player.mobile.search.listener.SearchResultClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Setter;

/**
 * Created by Jamie Cruwys of 3 SIDED CUBE on 04/04/2017.
 */
public class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
	@BindView(R.id.image) public ImageView image;
	@BindView(R.id.type) public TextView type;
	@BindView(R.id.title) public TextView title;

	private SearchResultClickListener searchResultClickListener = null;
	@Setter private SearchResult searchResult;

	public SearchViewHolder(View itemView, SearchResultClickListener searchResultClickListener)
	{
		super(itemView);
		ButterKnife.bind(this, itemView);

		this.searchResultClickListener = searchResultClickListener;
		itemView.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		if (searchResultClickListener != null && searchResult != null)
		{
			searchResultClickListener.selectedSearchResult(searchResult);
		}
	}
}