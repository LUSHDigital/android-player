package com.cube.lush.player.mobile.events.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.cube.lush.player.R;
import com.cube.lush.player.api.model.MediaContent;
import com.cube.lush.player.content.manager.MediaManager;
import com.cube.lush.player.mobile.base.BaseAdapter;
import com.cube.lush.player.mobile.base.EventTabSelection;
import com.cube.lush.player.mobile.base.RecyclerViewClickedListener;
import com.cube.lush.player.mobile.events.EventTab;
import com.cube.lush.player.mobile.events.holder.AllEventViewHolder;

import java.util.List;

/**
 * Created by Jamie Cruwys of 3 SIDED CUBE on 13/04/2017.
 */
public class AllEventsAdapter extends BaseAdapter<EventTab, AllEventViewHolder>
{
	@NonNull private Context context;
	@NonNull private List<MediaContent> items;
	@NonNull private RecyclerViewClickedListener<MediaContent> listener;
	@NonNull EventTabSelection tabListener;

	public AllEventsAdapter(@NonNull Context context, @NonNull List<EventTab> tabs, @NonNull List<MediaContent> items, @NonNull RecyclerViewClickedListener<MediaContent> listener, @NonNull EventTabSelection tabListener)
	{
		super(tabs);
		this.context = context;
		this.items = items;
		this.listener = listener;
		this.tabListener = tabListener;
	}

	@Override protected int provideViewHolderLayout()
	{
		return R.layout.event_all_item;
	}

	@NonNull @Override protected AllEventViewHolder createViewHolder(@NonNull View itemView)
	{
		return new AllEventViewHolder(itemView, null);
	}

	@Override protected void bind(@NonNull AllEventViewHolder holder, @NonNull final EventTab tab)
	{
		holder.title.setText(tab.getDisplayName());

		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
		holder.eventRecycler.setLayoutManager(linearLayoutManager);

		List<MediaContent> eventMediaContent = MediaManager.getInstance().filterContentByTag(tab.getTag(), items);
		EventsAdapter eventsAdapter = new EventsAdapter(eventMediaContent, listener);
		holder.eventRecycler.setAdapter(eventsAdapter);

		holder.moreButton.setOnClickListener(new View.OnClickListener()
		{
			@Override public void onClick(View v)
			{
				tabListener.selectTab(tab);
			}
		});
	}
}