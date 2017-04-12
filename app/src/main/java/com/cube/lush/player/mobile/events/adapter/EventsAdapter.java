package com.cube.lush.player.mobile.events.adapter;

import android.support.annotation.NonNull;
import android.view.View;

import com.cube.lush.player.R;
import com.cube.lush.player.api.model.MediaContent;
import com.cube.lush.player.mobile.base.BaseAdapter;
import com.cube.lush.player.mobile.base.RecyclerViewClickedListener;
import com.cube.lush.player.mobile.events.holder.EventViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Jamie Cruwys of 3 SIDED CUBE on 23/03/2017.
 */
public class EventsAdapter extends BaseAdapter<MediaContent, EventViewHolder>
{
	private RecyclerViewClickedListener listener;

	public EventsAdapter(@NonNull List<MediaContent> items, @NonNull RecyclerViewClickedListener listener)
	{
		super(items);
		this.listener = listener;
	}

	@Override protected int provideViewHolderLayout()
	{
		return R.layout.event_item;
	}

	@NonNull @Override protected EventViewHolder createViewHolder(@NonNull View itemView)
	{
		return new EventViewHolder(itemView, listener);
	}

	@Override protected void bind(@NonNull EventViewHolder holder, @NonNull MediaContent item)
	{
		holder.type.setText(item.getType().getName());
		holder.title.setText(item.getTitle());
		holder.length.setText(item.getRelativeDate());

		Picasso.with(holder.image.getContext())
			.load(item.getThumbnail())
			.fit()
			.centerCrop()
			.into(holder.image);
	}
}