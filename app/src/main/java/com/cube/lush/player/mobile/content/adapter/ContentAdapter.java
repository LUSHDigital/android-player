package com.cube.lush.player.mobile.content.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cube.lush.player.R;
import com.cube.lush.player.api.model.MediaContent;
import com.cube.lush.player.mobile.content.holder.ContentViewHolder;
import com.lush.lib.adapter.BaseSelectableListAdapter;
import com.lush.lib.listener.OnListItemClickListener;
import com.lush.view.holder.BaseViewHolder;

import java.util.List;

/**
 * Created by Jamie Cruwys.
 */
public class ContentAdapter extends BaseSelectableListAdapter<MediaContent>
{
	public ContentAdapter(List<MediaContent> items, OnListItemClickListener<MediaContent> listener)
	{
		super(items, listener);
	}

	@Override public BaseViewHolder<MediaContent> onCreateViewHolder(ViewGroup parent, int viewType)
	{
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_item, parent, false);
		return new ContentViewHolder(view);
	}
}