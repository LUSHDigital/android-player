package com.cube.lush.player.mobile.events.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cube.lush.player.R;
import com.cube.lush.player.api.model.MediaContent;
import com.cube.lush.player.mobile.events.holder.EventViewHolder;
import com.lush.lib.adapter.BaseSelectableListAdapter;
import com.lush.lib.listener.OnListItemClickListener;
import com.lush.view.holder.BaseViewHolder;

import java.util.List;

/**
 * Created by Jamie Cruwys of 3 SIDED CUBE on 23/03/2017.
 */
public class EventsAdapter extends BaseSelectableListAdapter<MediaContent>
{
	public EventsAdapter(List<MediaContent> items, OnListItemClickListener<MediaContent> listener)
	{
		super(items, listener);
	}

	@Override public BaseViewHolder<MediaContent> onCreateViewHolder(ViewGroup parent, int viewType)
	{
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);
		return new EventViewHolder(view);
	}
}