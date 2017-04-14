package com.cube.lush.player.mobile.search.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cube.lush.player.R;
import com.cube.lush.player.api.model.SearchResult;
import com.lush.view.holder.BaseViewHolder;
import com.squareup.picasso.Picasso;

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

	public SearchViewHolder(View view)
	{
		super(view);
		ButterKnife.bind(this, view);
	}

	@Override public void bind(SearchResult searchResult)
	{
		type.setText(searchResult.getType().getName());
		title.setText(searchResult.getTitle());

		Picasso.with(image.getContext())
			.load(searchResult.getThumbnail())
			.fit()
			.centerInside()
			.into(image);
	}
}