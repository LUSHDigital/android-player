package com.cube.lush.player.mobile.events.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cube.lush.player.R;
import com.cube.lush.player.api.model.MediaContent;
import com.cube.lush.player.mobile.base.EventTabSelection;
import com.cube.lush.player.mobile.events.EventTab;
import com.cube.lush.player.mobile.events.holder.AllEventViewHolder;
import com.lush.lib.adapter.BaseListAdapter;
import com.lush.lib.listener.OnListItemClickListener;
import com.lush.view.holder.BaseViewHolder;

import java.util.List;

/**
 * Created by Jamie Cruwys of 3 SIDED CUBE on 13/04/2017.
 */
public class AllEventsAdapter extends BaseListAdapter<EventTab>
{
	private final List<MediaContent> items;
	private final OnListItemClickListener<MediaContent> itemListener;
	private final EventTabSelection tabListener;

	public AllEventsAdapter(@NonNull List<MediaContent> items, @NonNull OnListItemClickListener<MediaContent> itemListener, @NonNull EventTabSelection tabListener)
	{
		super(EventTab.listValues(), null);
		this.items = items;
		this.itemListener = itemListener;
		this.tabListener = tabListener;
	}

	@Override public BaseViewHolder<EventTab> onCreateViewHolder(ViewGroup parent, int viewType)
	{
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_all_item, parent, false);
		return new AllEventViewHolder(view, items, itemListener, tabListener);
	}
}