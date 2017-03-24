package com.cube.lush.player.mobile.content.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cube.lush.player.R;
import com.cube.lush.player.api.model.MediaContent;
import com.cube.lush.player.content.model.Channel;
import com.cube.lush.player.mobile.channels.listener.ChannelClickListener;
import com.cube.lush.player.mobile.content.listener.ContentClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Setter;

/**
 * Created by Jamie Cruwys of 3 SIDED CUBE on 23/03/2017.
 */
public class ContentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

	@BindView(R.id.image) public ImageView image;
	@BindView(R.id.type) public TextView type;
	@BindView(R.id.title) public TextView title;
	@BindView(R.id.length) public TextView length;

	private ContentClickListener contentClickListener = null;
	@Setter private MediaContent mediaContent;

	public ContentViewHolder(View itemView, ContentClickListener contentClickListener)
	{
		super(itemView);
		ButterKnife.bind(this, itemView);

		this.contentClickListener = contentClickListener;
		itemView.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		if (contentClickListener != null && mediaContent != null)
		{
			contentClickListener.selectedContent(mediaContent);
		}
	}
}