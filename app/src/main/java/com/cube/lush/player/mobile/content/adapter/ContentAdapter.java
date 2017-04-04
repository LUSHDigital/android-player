package com.cube.lush.player.mobile.content.adapter;

import android.support.annotation.NonNull;
import android.view.View;

import com.cube.lush.player.R;
import com.cube.lush.player.api.model.MediaContent;
import com.cube.lush.player.mobile.base.BaseAdapter;
import com.cube.lush.player.mobile.base.RecyclerViewClickedListener;
import com.cube.lush.player.mobile.content.holder.ContentViewHolder;
import com.cube.lush.player.mobile.content.listener.ContentClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Jamie Cruwys of 3 SIDED CUBE on 04/04/2017.
 */
public class ContentAdapter extends BaseAdapter<MediaContent, ContentViewHolder>
{
	private RecyclerViewClickedListener listener = null;

	public ContentAdapter(@NonNull List<MediaContent> items, @NonNull RecyclerViewClickedListener listener)
	{
		super(items);
		this.listener = listener;
	}

	@Override protected int provideViewHolderLayout()
	{
		return R.layout.mobile_item_content;
	}

	@NonNull @Override protected ContentViewHolder createViewHolder(@NonNull View itemView)
	{
		return new ContentViewHolder(itemView, listener);
	}

	@Override protected void bind(@NonNull ContentViewHolder holder, @NonNull MediaContent item)
	{
		holder.setItem(item);

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