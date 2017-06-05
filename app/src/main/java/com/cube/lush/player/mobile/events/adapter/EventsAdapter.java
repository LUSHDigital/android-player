package com.cube.lush.player.mobile.events.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cube.lush.player.R;
import com.cube.lush.player.api.model.Programme;
import com.cube.lush.player.mobile.events.EventTab;
import com.cube.lush.player.mobile.events.EventTabSelection;
import com.cube.lush.player.mobile.events.holder.EventViewHolder;
import com.lush.lib.adapter.BaseListAdapter;
import com.lush.lib.listener.OnListItemClickListener;
import com.lush.view.holder.BaseViewHolder;

import java.util.List;

/**
 * Events Adapter
 *
 * @author Jamie Cruwys
 */
public class EventsAdapter extends BaseListAdapter<EventTab>
{
	private final List<Programme> items;
	private final OnListItemClickListener<Programme> itemListener;
	private final EventTabSelection tabListener;

	public EventsAdapter(@NonNull List<Programme> items, @NonNull OnListItemClickListener<Programme> itemListener, @NonNull EventTabSelection tabListener)
	{
		super(EventTab.listValuesExcluding(EventTab.ALL), null);
		this.items = items;
		this.itemListener = itemListener;
		this.tabListener = tabListener;
	}

	@Override public BaseViewHolder<EventTab> onCreateViewHolder(ViewGroup parent, int viewType)
	{
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_all_item, parent, false);
		return new EventViewHolder(view, items, itemListener, tabListener);
	}
}