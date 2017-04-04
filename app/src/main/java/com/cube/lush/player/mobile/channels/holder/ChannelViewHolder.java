package com.cube.lush.player.mobile.channels.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.cube.lush.player.R;
import com.cube.lush.player.content.model.Channel;
import com.cube.lush.player.mobile.base.BaseViewHolder;
import com.cube.lush.player.mobile.base.RecyclerViewClickedListener;
import com.cube.lush.player.mobile.channels.listener.ChannelClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Setter;

/**
 * Created by Jamie Cruwys of 3 SIDED CUBE on 23/03/2017.
 */
public class ChannelViewHolder extends BaseViewHolder<Channel>
{
	@BindView(R.id.image) public ImageView image;

	public ChannelViewHolder(@NonNull View itemView, @NonNull RecyclerViewClickedListener<Channel> listener)
	{
		super(itemView, listener);
		ButterKnife.bind(this, itemView);
	}
}