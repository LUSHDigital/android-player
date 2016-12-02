package com.cube.lush.player.presenter;

import android.content.Context;
import android.support.v17.leanback.widget.Presenter;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cube.lush.player.R;
import com.cube.lush.player.model.MediaContent;
import com.cube.lush.player.view.CardView;
import com.nostra13.universalimageloader.core.ImageLoader;

import lombok.Data;

import static android.text.format.DateUtils.DAY_IN_MILLIS;

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

		View titleView = cardView.findViewById(R.id.title_text);

		if (titleView instanceof TextView)
		{
			((TextView) titleView).setMaxLines(2);
		}

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

		// We assume the date is in UTC as the Lush API doesn't specify otherwise
		long now = System.currentTimeMillis();
		long time = Math.min(mediaContent.getDate().getTime(), now); // Make sure we don't show content as being in the future
		CharSequence description = DateUtils.getRelativeTimeSpanString(time, now, DAY_IN_MILLIS);

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
		private CardView cardView;

		public MediaViewHolder(View view) {
			super(view);
			cardView = (CardView) view;
		}
	}
}
