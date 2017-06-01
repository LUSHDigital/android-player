package com.cube.lush.player.mobile.channels.holder;

import android.view.View;
import android.widget.ImageView;

import com.cube.lush.player.R;
import com.cube.lush.player.api.model.Channel;
import com.lush.view.holder.BaseViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Channel View Holder
 *
 * @author Jamie Cruwys
 */
public class ChannelViewHolder extends BaseViewHolder<Channel>
{
	@BindView(R.id.image) public ImageView image;

	public ChannelViewHolder(View view)
	{
		super(view);
		ButterKnife.bind(this, view);
	}

	@Override public void bind(Channel channel)
	{
		// channel.getImage()
		// TODO: Load channel.getImage() into the image view
		// Drawable drawable = ContextCompat.getDrawable(image.getContext(), channel.getLogo());
		// image.setImageDrawable(drawable);
	}
}