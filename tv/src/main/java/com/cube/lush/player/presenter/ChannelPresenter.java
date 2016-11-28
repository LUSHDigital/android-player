package com.cube.lush.player.presenter;

import android.content.Context;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.Presenter;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.cube.lush.player.model.Channel;
import com.nostra13.universalimageloader.core.ImageLoader;

import lombok.Data;

/**
 * @author Jamie Cruwys
 * @project lush-player-android-client
 */
public class ChannelPresenter extends Presenter
{
	private static int CARD_WIDTH = 400;
	private static int CARD_HEIGHT = 180;

	@Override public ViewHolder onCreateViewHolder(ViewGroup parent)
	{
		Context context = parent.getContext();

		ImageCardView cardView = new ImageCardView(context);
		cardView.setFocusable(true);
		cardView.setFocusableInTouchMode(true);

		return new ChannelViewHolder(cardView);
	}

	@Override public void onBindViewHolder(ViewHolder viewHolder, Object item)
	{
		Channel channel = (Channel)item;
		ChannelViewHolder mediaViewHolder = (ChannelViewHolder)viewHolder;
		ImageCardView cardView = mediaViewHolder.getCardView();

		String title = channel.getTitle();

		if (!TextUtils.isEmpty(title))
		{
			cardView.setTitleText(title);
		}
		else
		{
			cardView.setTitleText("");
		}

		String description = channel.getDescription();

		if (!TextUtils.isEmpty(description))
		{
			cardView.setContentText(description);
		}
		else
		{
			cardView.setContentText("");
		}

		cardView.setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT);

		ImageLoader.getInstance().displayImage("http://intu.co.uk/uploads/media/logo_retailer/0001/06/thumb_5128_logo_retailer_1x.png", cardView.getMainImageView());
	}

	@Override public void onUnbindViewHolder(ViewHolder viewHolder)
	{

	}

	@Data
	static class ChannelViewHolder extends ViewHolder {
		private Channel channel;
		private ImageCardView cardView;

		public ChannelViewHolder(View view) {
			super(view);
			cardView = (ImageCardView) view;
		}
	}
}
