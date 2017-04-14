package com.cube.lush.player.mobile.events.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cube.lush.player.R;
import com.cube.lush.player.api.model.MediaContent;
import com.lush.view.holder.BaseViewHolder;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jamie Cruwys of 3 SIDED CUBE on 07/04/2017.
 */
public class EventViewHolder extends BaseViewHolder<MediaContent>
{
	@BindView(R.id.image) public ImageView image;
	@BindView(R.id.type) public TextView type;
	@BindView(R.id.title) public TextView title;
	@BindView(R.id.length) public TextView length;

	public EventViewHolder(View view)
	{
		super(view);
		ButterKnife.bind(this, view);
	}

	@Override public void bind(MediaContent mediaContent)
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