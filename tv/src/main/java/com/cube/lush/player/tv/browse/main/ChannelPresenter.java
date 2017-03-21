package com.cube.lush.player.tv.browse.main;

import android.content.Context;
import android.support.v17.leanback.widget.Presenter;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cube.lush.player.R;
import com.cube.lush.player.content.model.Channel;
import com.cube.lush.player.tv.view.CardView;

import lombok.Data;

/**
 * Presents information about Lush channels in a {@link CardView}.
 *
 * @author Jamie Cruwys
 * @project lush-player-android-client
 */
public class ChannelPresenter extends Presenter
{
	@Override public ViewHolder onCreateViewHolder(ViewGroup parent)
	{
		Context context = parent.getContext();

		CardView cardView = new CardView(context);
		cardView.setSelectionBorderEnabled(false);
		return new ChannelViewHolder(cardView);
	}

	@Override public void onBindViewHolder(ViewHolder viewHolder, Object item)
	{
		Channel channel = (Channel)item;
		ChannelViewHolder mediaViewHolder = (ChannelViewHolder)viewHolder;
		CardView cardView = mediaViewHolder.getCardView();

		String description = channel.getDescription();

		if (!TextUtils.isEmpty(description))
		{
			cardView.setContentText(description);
		}
		else
		{
			cardView.setContentText("");
		}

		int width = cardView.getContext().getResources().getDimensionPixelSize(R.dimen.channel_card_width);
		int imageHeight = cardView.getContext().getResources().getDimensionPixelSize(R.dimen.channel_card_height);
		cardView.setMainImageDimensions(width, imageHeight);

		cardView.setMainImageScaleType(ImageView.ScaleType.CENTER_INSIDE);
		cardView.getMainImageView().setPadding(16, 16, 16, 16);
		cardView.getMainImageView().setImageResource(channel.getLogo());
	}

	@Override public void onUnbindViewHolder(ViewHolder viewHolder)
	{

	}

	@Data
	static class ChannelViewHolder extends ViewHolder {
		private Channel channel;
		private CardView cardView;

		public ChannelViewHolder(View view) {
			super(view);
			cardView = (CardView) view;
		}
	}
}
