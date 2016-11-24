package com.cube.lush.player.presenter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.Presenter;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;

import com.cube.lush.player.R;
import com.cube.lush.player.model.MediaContent;

import lombok.Data;

/**
 * @author Jamie Cruwys
 * @project lush-player-android-client
 */
public class MediaPresenter extends Presenter
{
	private static int CARD_WIDTH = 320;
	private static int CARD_HEIGHT = 180;

	@Override public ViewHolder onCreateViewHolder(ViewGroup parent)
	{
		Context context = parent.getContext();

		ImageCardView cardView = new ImageCardView(context);
		cardView.setFocusable(true);
		cardView.setFocusableInTouchMode(true);

		int backgroundColor = ContextCompat.getColor(context, R.color.black);
		cardView.setBackgroundColor(backgroundColor);

		return new MediaViewHolder(cardView);
	}

	@Override public void onBindViewHolder(ViewHolder viewHolder, Object item)
	{
		MediaContent mediaContent = (MediaContent)item;
		MediaViewHolder mediaViewHolder = (MediaViewHolder)viewHolder;
		ImageCardView cardView = mediaViewHolder.getCardView();

		cardView.setTitleText(mediaContent.getTitle());
		cardView.setContentText(mediaContent.getDescription());
		cardView.setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT);

		Drawable mainImageDrawable = ContextCompat.getDrawable(cardView.getContext(), R.mipmap.ic_launcher);
		cardView.setMainImage(mainImageDrawable);
	}

	@Override public void onUnbindViewHolder(ViewHolder viewHolder)
	{

	}

	@Data
	static class MediaViewHolder extends Presenter.ViewHolder {
		private MediaContent content;
		private ImageCardView cardView;

		public MediaViewHolder(View view) {
			super(view);
			cardView = (ImageCardView) view;
		}
	}
}