package com.cube.lush.player.mobile.channels.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.cube.lush.player.mobile.R2;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jamie Cruwys of 3 SIDED CUBE on 23/03/2017.
 */
public class ChannelViewHolder extends RecyclerView.ViewHolder
{
	@BindView(R2.id.image) public ImageView image;

	public ChannelViewHolder(View itemView)
	{
		super(itemView);
		ButterKnife.bind(this, itemView);
	}
}