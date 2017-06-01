package com.cube.lush.player.mobile.content.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cube.lush.player.R;
import com.cube.lush.player.api.model.Programme;
import com.lush.view.holder.BaseViewHolder;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jamie Cruwys.
 */
public class ContentViewHolder extends BaseViewHolder<Programme>
{
	@BindView(R.id.image) public ImageView image;
	@BindView(R.id.type) public TextView type;
	@BindView(R.id.title) public TextView title;
	@BindView(R.id.length) public TextView length;

	public ContentViewHolder(View view)
	{
		super(view);
		ButterKnife.bind(this, view);
	}

	@Override public void bind(Programme mediaContent)
	{
		type.setText(mediaContent.getType().getName());
		title.setText(mediaContent.getTitle());
		length.setText(mediaContent.getRelativeDate());

		Picasso.with(image.getContext())
			.load(mediaContent.getThumbnail())
			.fit()
			.centerCrop()
			.into(image);
	}
}