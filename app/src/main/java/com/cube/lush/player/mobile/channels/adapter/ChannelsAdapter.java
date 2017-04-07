package com.cube.lush.player.mobile.channels.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.cube.lush.player.R;
import com.cube.lush.player.content.model.Channel;
import com.cube.lush.player.mobile.base.BaseAdapter;
import com.cube.lush.player.mobile.base.RecyclerViewClickedListener;
import com.cube.lush.player.mobile.channels.holder.ChannelViewHolder;

import java.util.List;

/**
 * Created by Jamie Cruwys of 3 SIDED CUBE on 23/03/2017.
 */
public class ChannelsAdapter extends BaseAdapter<Channel, ChannelViewHolder>
{
	private RecyclerViewClickedListener listener;

	public ChannelsAdapter(@NonNull List<Channel> items, @NonNull RecyclerViewClickedListener listener)
	{
		super(items);
		this.listener = listener;
	}

	@Override protected int provideViewHolderLayout()
	{
		return R.layout.mobile_item_channels;
	}

	@NonNull @Override protected ChannelViewHolder createViewHolder(@NonNull View itemView)
	{
		return new ChannelViewHolder(itemView, listener);
	}

	@Override protected void bind(@NonNull ChannelViewHolder holder, @NonNull Channel item)
	{
		Drawable drawable = ContextCompat.getDrawable(holder.image.getContext(), item.getLogo());
		holder.image.setImageDrawable(drawable);
	}
}