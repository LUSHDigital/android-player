package com.cube.lush.player.presenter;

import android.content.Context;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.Presenter;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.cube.lush.player.R;
import com.cube.lush.player.model.MediaContent;
import com.nostra13.universalimageloader.core.ImageLoader;

import lombok.Data;

/**
 * @author Jamie Cruwys
 * @project lush-player-android-client
 */
public class MediaPresenter extends Presenter
{
	private static int CARD_WIDTH = 400;
	private static int CARD_HEIGHT = 180;

	@Override public ViewHolder onCreateViewHolder(ViewGroup parent)
	{
		Context context = parent.getContext();

		ImageCardView cardView = new ImageCardView(context);
		cardView.setFocusable(true);
		cardView.setFocusableInTouchMode(true);

		return new MediaViewHolder(cardView);
	}

	@Override public void onBindViewHolder(ViewHolder viewHolder, Object item)
	{
		MediaContent mediaContent = (MediaContent)item;
		MediaViewHolder mediaViewHolder = (MediaViewHolder)viewHolder;
		ImageCardView cardView = mediaViewHolder.getCardView();

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

		cardView.setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT);

		ImageLoader.getInstance().displayImage(mediaContent.getThumbnail(), cardView.getMainImageView());
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
