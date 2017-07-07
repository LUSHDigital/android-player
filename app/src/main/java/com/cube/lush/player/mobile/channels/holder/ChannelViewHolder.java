package com.cube.lush.player.mobile.channels.holder;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cube.lush.player.LushImageLoader;
import com.cube.lush.player.R;
import com.lush.player.api.model.Channel;
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
	@BindView(R.id.alternative_text) public TextView alternativeText;

	public ChannelViewHolder(View view)
	{
		super(view);
		ButterKnife.bind(this, view);
	}

	@Override public void bind(Channel channel)
	{
		if (channel == null)
		{
			return;
		}

		String imageUri = channel.getImage();

		if (TextUtils.isEmpty(imageUri))
		{
			image.setVisibility(View.GONE);

			alternativeText.setText(channel.getName());
			alternativeText.setVisibility(View.VISIBLE);
			return;
		}

		LushImageLoader.display(imageUri, image);
	}

	@Override
	public void recycle()
	{
		super.recycle();

		LushImageLoader.cancelDisplay(image);
	}
}