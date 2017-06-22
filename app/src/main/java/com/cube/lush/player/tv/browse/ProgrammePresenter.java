package com.cube.lush.player.tv.browse;

import android.content.Context;
import android.support.v17.leanback.widget.Presenter;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;

import com.cube.lush.player.R;
import com.lush.player.api.model.Programme;
import com.cube.lush.player.tv.view.CardView;
import com.squareup.picasso.Picasso;

import lombok.Data;

import static android.text.format.DateUtils.DAY_IN_MILLIS;

/**
 * Presents information about various types of {@link Programme} inside a {@link CardView}.
 *
 * @author Jamie Cruwys
 */
public class ProgrammePresenter extends Presenter
{
	@Override public ViewHolder onCreateViewHolder(ViewGroup parent)
	{
		Context context = parent.getContext();

		CardView cardView = new CardView(context);

		return new ProgrammeViewHolder(cardView);
	}

	@Override public void onBindViewHolder(ViewHolder viewHolder, Object item)
	{
		Programme programme = (Programme)item;
		ProgrammeViewHolder programmeViewHolder = (ProgrammeViewHolder)viewHolder;
		CardView cardView = programmeViewHolder.getCardView();

		if (programme.getType() != null)
		{
			cardView.getMediaTypeView().setVisibility(View.VISIBLE);
			cardView.setMediaText(programme.getType().getName());
		}
		else
		{
			cardView.getMediaTypeView().setVisibility(View.GONE);
		}

		String title = programme.getTitle();

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
		if (programme.getDate() != null)
		{
			long now = System.currentTimeMillis();
			long time = Math.min(programme.getDate().getTime(), now); // Make sure we don't show content as being in the future
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
			.load(programme.getThumbnail())
			.into(cardView.getMainImageView());
	}

	@Override public void onUnbindViewHolder(ViewHolder viewHolder)
	{

	}

	@Data
	static class ProgrammeViewHolder extends ViewHolder {
		private Programme programme;
		private CardView cardView;

		public ProgrammeViewHolder(View view) {
			super(view);
			cardView = (CardView) view;
		}
	}
}
