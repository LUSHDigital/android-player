package com.cube.lush.player.mobile.channels.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cube.lush.player.R;
import com.cube.lush.player.content.model.Channel;
import com.cube.lush.player.mobile.channels.holder.ChannelViewHolder;
import com.cube.lush.player.mobile.channels.listener.ChannelClickListener;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;

/**
 * Created by Jamie Cruwys of 3 SIDED CUBE on 23/03/2017.
 */
@AllArgsConstructor
public class ChannelsAdapter extends RecyclerView.Adapter<ChannelViewHolder>
{
	private @NonNull List<Channel> channels = new ArrayList<Channel>();
	private @NonNull ChannelClickListener channelClickListener = null;

	@Override public ChannelViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_channels, parent, false);
		return new ChannelViewHolder(itemView, channelClickListener);
	}

	@Override public void onBindViewHolder(ChannelViewHolder holder, int position)
	{
		Channel channel = channels.get(position);
		holder.setChannel(channel);

		Drawable drawable = ContextCompat.getDrawable(holder.image.getContext(), channel.getLogo());
		holder.image.setImageDrawable(drawable);
	}

	@Override public int getItemCount()
	{
		return channels.size();
	}
}