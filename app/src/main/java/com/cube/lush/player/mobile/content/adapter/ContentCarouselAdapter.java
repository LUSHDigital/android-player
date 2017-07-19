package com.cube.lush.player.mobile.content.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cube.lush.player.R;
import com.lush.player.api.model.Programme;
import com.cube.lush.player.mobile.content.holder.ContentViewHolder;
import com.lush.lib.adapter.BaseSelectableListAdapter;
import com.lush.lib.listener.OnListItemClickListener;
import com.lush.view.holder.BaseViewHolder;

import java.util.List;

/**
 * Content Adapter for the horizontal scrolling carousel
 *
 * @author Jamie Cruwys
 */
public class ContentCarouselAdapter extends BaseSelectableListAdapter<Programme>
{
	public ContentCarouselAdapter(List<Programme> items, OnListItemClickListener<Programme> listener)
	{
		super(items, listener);
	}

	@Override public BaseViewHolder<Programme> onCreateViewHolder(ViewGroup parent, int viewType)
	{
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_carousel_item, parent, false);
		return new ContentViewHolder(view);
	}
}