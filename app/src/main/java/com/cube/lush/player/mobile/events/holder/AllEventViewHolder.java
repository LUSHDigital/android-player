package com.cube.lush.player.mobile.events.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cube.lush.player.R;
import com.cube.lush.player.api.model.MediaContent;
import com.cube.lush.player.content.manager.MediaManager;
import com.cube.lush.player.mobile.base.EventTabSelection;
import com.cube.lush.player.mobile.events.EventTab;
import com.cube.lush.player.mobile.events.adapter.EventsAdapter;
import com.lush.lib.listener.OnListItemClickListener;
import com.lush.view.holder.BaseViewHolder;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Jamie Cruwys of 3 SIDED CUBE on 13/04/2017.
 */
public class AllEventViewHolder extends BaseViewHolder<EventTab>
{
	@BindView(R.id.section_title) public TextView title;
	@BindView(R.id.event_recycler) public RecyclerView eventRecycler;
	@BindView(R.id.event_more) public Button moreButton;

	private final List<MediaContent> items;
	private final OnListItemClickListener<MediaContent> itemListener;
	private final EventTabSelection tabListener;

	public AllEventViewHolder(@NonNull View view, @NonNull List<MediaContent> items, @NonNull OnListItemClickListener<MediaContent> itemListener, @NonNull EventTabSelection tabListener)
	{
		super(view);
		this.items = items;
		this.itemListener = itemListener;
		this.tabListener = tabListener;
	}

	@Override public void bind(final EventTab eventTab)
	{
		title.setText(eventTab.getDisplayName());

		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(eventRecycler.getContext(), LinearLayoutManager.HORIZONTAL, false);
		eventRecycler.setLayoutManager(linearLayoutManager);

		List<MediaContent> eventMediaContent = MediaManager.getInstance().filterContentByTag(eventTab.getTag(), items);
		EventsAdapter eventsAdapter = new EventsAdapter(eventMediaContent, itemListener);
		eventRecycler.setAdapter(eventsAdapter);

		moreButton.setOnClickListener(new View.OnClickListener()
		{
			@Override public void onClick(View v)
			{
				tabListener.selectTab(eventTab);
			}
		});
	}
}