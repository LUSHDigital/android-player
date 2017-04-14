package com.cube.lush.player.mobile.channels.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cube.lush.player.R;
import com.cube.lush.player.content.model.Channel;
import com.cube.lush.player.mobile.channels.holder.ChannelViewHolder;
import com.lush.lib.adapter.BaseSelectableListAdapter;
import com.lush.lib.listener.OnListItemClickListener;
import com.lush.view.holder.BaseViewHolder;

import java.util.List;

/**
 * Created by Jamie Cruwys.
 */
public class ChannelsAdapter extends BaseSelectableListAdapter<Channel>
{
	public ChannelsAdapter(List<Channel> items, OnListItemClickListener<Channel> listener)
	{
		super(items, listener);
	}

	@Override public BaseViewHolder<Channel> onCreateViewHolder(ViewGroup parent, int viewType)
	{
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.channel_item, parent, false);
		return new ChannelViewHolder(view);
	}
}