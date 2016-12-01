package com.cube.lush.player.presenter;

import android.content.Context;
import android.support.v17.leanback.widget.Presenter;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.cube.lush.player.R;
import com.cube.lush.player.model.MediaContent;
import com.cube.lush.player.view.CardView;
import com.nostra13.universalimageloader.core.ImageLoader;

import lombok.Data;

/**
 * @author Jamie Cruwys
 * @project lush-player-android-client
 */
public class MediaPresenter extends Presenter
{
	private static int CARD_WIDTH = 300;
	private static int CARD_HEIGHT = 150;

	@Override public ViewHolder onCreateViewHolder(ViewGroup parent)
	{
		Context context = parent.getContext();

		CardView cardView = new CardView(context);
		return new MediaViewHolder(cardView);
	}

	@Override public void onBindViewHolder(ViewHolder viewHolder, Object item)
	{
		MediaContent mediaContent = (MediaContent)item;
		MediaViewHolder mediaViewHolder = (MediaViewHolder)viewHolder;
		CardView cardView = mediaViewHolder.getCardView();

		String title = mediaContent.getTitle();

		if (!TextUtils.isEmpty(title))
		{
			cardView.setTitleText(title);
		}
		else
		{
			cardView.setTitleText("");
		}

		String description = mediaContent.getDescription();

		if (!TextUtils.isEmpty(description))
		{
			cardView.setContentText(description);
		}
		else
		{
			cardView.setContentText("");
		}

		cardView.setBadgeImage(cardView.getContext().getResources().getDrawable(R.drawable.ic_radio_white_36dp));

		cardView.setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT);

		ImageLoader.getInstance().displayImage(mediaContent.getThumbnail(), cardView.getMainImageView());
	}

	@Override public void onUnbindViewHolder(ViewHolder viewHolder)
	{

	}

	@Data
	static class MediaViewHolder extends Presenter.ViewHolder {
		private MediaContent content;
		private CardView cardView;

		public MediaViewHolder(View view) {
			super(view);
			cardView = (CardView) view;
		}
	}
}
