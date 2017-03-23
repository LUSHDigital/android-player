package com.cube.lush.player.mobile.channels.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.cube.lush.player.content.model.Channel;
import com.cube.lush.player.mobile.R2;
import com.cube.lush.player.mobile.channels.listener.ChannelClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Setter;

/**
 * Created by Jamie Cruwys of 3 SIDED CUBE on 23/03/2017.
 */
public class ChannelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
	@BindView(R2.id.image) public ImageView image;
	private ChannelClickListener channelClickListener = null;
	@Setter private Channel channel;

	public ChannelViewHolder(View itemView, ChannelClickListener channelClickListener)
	{
		super(itemView);
		ButterKnife.bind(this, itemView);

		this.channelClickListener = channelClickListener;
		itemView.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		if (channelClickListener != null && channel != null)
		{
			channelClickListener.selectedChannel(channel);
		}
	}
}