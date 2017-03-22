package com.cube.lush.player.tv.browse;

import android.content.Context;
import android.support.v17.leanback.widget.Presenter;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;

import com.cube.lush.player.tv.R;
import com.cube.lush.player.api.model.MediaContent;
import com.cube.lush.player.tv.view.CardView;
import com.squareup.picasso.Picasso;

import lombok.Data;

import static android.text.format.DateUtils.DAY_IN_MILLIS;

/**
 * Presents information about various types of {@link MediaContent} inside a {@link CardView}.
 *
 * @author Jamie Cruwys
 * @project lush-player-android-client
 */
public class MediaPresenter extends Presenter
{
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

		if (mediaContent.getType() != null)
		{
			switch (mediaContent.getType())
			{
				case RADIO:
				{
					cardView.getMediaTypeView().setVisibility(View.VISIBLE);
					cardView.setMediaText("RADIO");
					break;
				}
				case TV:
				{
					cardView.getMediaTypeView().setVisibility(View.VISIBLE);
					cardView.setMediaText("TV");
					break;
				}
				default:
				{
					cardView.getMediaTypeView().setVisibility(View.GONE);
					break;
				}
			}
		}
		else
		{
			cardView.getMediaTypeView().setVisibility(View.GONE);
		}

		String title = mediaContent.getTitle();

		if (!TextUtils.isEmpty(title))
		{
			cardView.getTitleView().setVisibility(View.VISIBLE);
			cardView.setTitleText(title);
		}
		else
		{
			cardView.getTitleView().setVisibility(View.GONE);
		}

		// We assume the date is in UTC as the Lush API doesn't specify otherwise
		if (mediaContent.getDate() != null)
		{
			long now = System.currentTimeMillis();
			long time = Math.min(mediaContent.getDate().getTime(), now); // Make sure we don't show content as being in the future
			CharSequence description = DateUtils.getRelativeTimeSpanString(time, now, DAY_IN_MILLIS);

			if (!TextUtils.isEmpty(description))
			{
				cardView.getContentView().setVisibility(View.VISIBLE);
				cardView.setContentText(description);
			}
			else
			{
				cardView.getContentView().setVisibility(View.GONE);
			}
		}
		else
		{
			cardView.getContentView().setVisibility(View.GONE);
		}

		int height = cardView.getContext().getResources().getDimensionPixelSize(R.dimen.card_height);
		int width = cardView.getContext().getResources().getDimensionPixelSize(R.dimen.card_width);
		int imageHeight = cardView.getContext().getResources().getDimensionPixelSize(R.dimen.card_image_height);

		cardView.getLayoutParams().height = height;
		cardView.getLayoutParams().width = width;

		cardView.setMainImageDimensions(width, imageHeight);

		Picasso.with(cardView.getContext())
			.load(mediaContent.getThumbnail())
			.into(cardView.getMainImageView());
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
