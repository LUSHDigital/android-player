package com.cube.lush.player.mobile.search.holder;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cube.lush.player.R;
import com.cube.lush.player.api.model.SearchResult;
import com.cube.lush.player.mobile.base.BaseViewHolder;
import com.cube.lush.player.mobile.base.RecyclerViewClickedListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jamie Cruwys of 3 SIDED CUBE on 04/04/2017.
 */
public class SearchViewHolder extends BaseViewHolder<SearchResult>
{
	@BindView(R.id.image) public ImageView image;
	@BindView(R.id.type) public TextView type;
	@BindView(R.id.title) public TextView title;

	public SearchViewHolder(@NonNull View itemView, @NonNull RecyclerViewClickedListener<SearchResult> listener)
	{
		super(itemView, listener);
		ButterKnife.bind(this, itemView);
	}
}