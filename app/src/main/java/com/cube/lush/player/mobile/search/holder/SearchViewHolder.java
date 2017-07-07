package com.cube.lush.player.mobile.search.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cube.lush.player.LushImageLoader;
import com.cube.lush.player.R;
import com.lush.player.api.model.Programme;
import com.lush.view.holder.BaseViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Search View Holder
 *
 * @author Jamie Cruwys
 */
public class SearchViewHolder extends BaseViewHolder<Programme>
{
	@BindView(R.id.image) public ImageView image;
	@BindView(R.id.type) public TextView type;
	@BindView(R.id.title) public TextView title;

	public SearchViewHolder(View view)
	{
		super(view);
		ButterKnife.bind(this, view);
	}

	@Override public void bind(Programme searchResult)
	{
		type.setText(searchResult.getType().getName());
		title.setText(searchResult.getTitle());

		LushImageLoader.display(searchResult.getThumbnail(), image);
	}

	@Override
	public void recycle()
	{
		super.recycle();

		LushImageLoader.cancelDisplay(image);
	}
}