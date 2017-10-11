package com.cube.lush.player.tv.channels;

import android.content.Context;
import android.support.v17.leanback.widget.Presenter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cube.lush.player.LushImageLoader;
import com.cube.lush.player.R;
import com.cube.lush.player.tv.view.CardView;
import com.lush.player.api.model.Channel;
import com.squareup.picasso.Picasso;

/**
 * Presents information about Lush channels in a {@link CardView}.
 *
 * @author Jamie Cruwys
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

		int width = cardView.getContext().getResources().getDimensionPixelSize(R.dimen.channel_card_width);
		int imageHeight = cardView.getContext().getResources().getDimensionPixelSize(R.dimen.channel_card_height);
		cardView.setMainImageDimensions(width, imageHeight);

		cardView.setMainImageScaleType(ImageView.ScaleType.CENTER_INSIDE);
		cardView.getMainImageView().setPadding(16, 16, 16, 16);

		Picasso.with(cardView.getContext())
			.load(channel.getImage())
			.fit()
			.centerInside()
			.into(cardView.getMainImageView());
	}

	@Override public void onUnbindViewHolder(ViewHolder viewHolder)
	{
		ChannelViewHolder mediaViewHolder = (ChannelViewHolder)viewHolder;
		CardView cardView = mediaViewHolder.getCardView();

		LushImageLoader.cancelDisplay(cardView.getMainImageView());
	}

	static class ChannelViewHolder extends ViewHolder {
		private Channel channel;
		private CardView cardView;

		public ChannelViewHolder(View view) {
			super(view);
			cardView = (CardView) view;
		}

		public Channel getChannel()
		{
			return channel;
		}

		public void setChannel(Channel channel)
		{
			this.channel = channel;
		}

		public CardView getCardView()
		{
			return cardView;
		}

		public void setCardView(CardView cardView)
		{
			this.cardView = cardView;
		}
	}
}
