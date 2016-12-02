package com.cube.lush.player.presenter;

import android.content.Context;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.Presenter;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.cube.lush.player.model.ContentType;
import com.cube.lush.player.model.MediaContent;
import com.cube.lush.player.model.SearchResult;
import com.nostra13.universalimageloader.core.ImageLoader;

import lombok.Data;

/**
 * @author Jamie Cruwys
 * @project lush-player-android-client
 */
public class SearchResultPresenter extends Presenter
{
	private static int CARD_WIDTH = 400;
	private static int CARD_HEIGHT = 180;

	@Override public Presenter.ViewHolder onCreateViewHolder(ViewGroup parent)
	{
		Context context = parent.getContext();

		ImageCardView cardView = new ImageCardView(context);
		cardView.setFocusable(true);
		cardView.setFocusableInTouchMode(true);

		return new SearchResultPresenter.SearchResultViewHolder(cardView);
	}

	@Override public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item)
	{
		SearchResult searchResult = (SearchResult)item;
		SearchResultViewHolder searchResultViewHolder = (SearchResultViewHolder)viewHolder;
		ImageCardView cardView = searchResultViewHolder.getCardView();

		String title = searchResult.getTitle();

		if (!TextUtils.isEmpty(title))
		{
			cardView.setTitleText(title);
		}
		else
		{
			cardView.setTitleText("");
		}

		ContentType type = searchResult.getType();

		String description = "";

		if (type != null && TextUtils.isEmpty(type.getName()))
		{
			description = type.getName().toUpperCase();
		}

		if (!TextUtils.isEmpty(description))
		{
			cardView.setContentText(description);
		}
		else
		{
			cardView.setContentText("");
		}

		cardView.setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT);

		ImageLoader.getInstance().displayImage(searchResult.getThumbnail(), cardView.getMainImageView());
	}

	@Override public void onUnbindViewHolder(Presenter.ViewHolder viewHolder)
	{

	}

	@Data
	static class SearchResultViewHolder extends Presenter.ViewHolder {
		private MediaContent content;
		private ImageCardView cardView;

		public SearchResultViewHolder(View view) {
			super(view);
			cardView = (ImageCardView) view;
		}
	}
}
