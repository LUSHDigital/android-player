package com.cube.lush.player.presenter;

import android.support.v17.leanback.widget.AbstractDetailsDescriptionPresenter;

import com.cube.lush.player.model.MediaContent;

/**
 * Created by tim on 24/11/2016.
 */
public class MediaDetailsPresenter extends AbstractDetailsDescriptionPresenter
{
	@Override
	protected void onBindDescription(ViewHolder vh, Object item)
	{
		if (item instanceof MediaContent)
		{
			MediaContent media = (MediaContent) item;
            vh.getTitle().setText(media.getTitle());
            vh.getSubtitle().setText(media.getDate().toString());
            vh.getBody().setText(media.getDescription());
		}
	}
}
