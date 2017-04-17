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
import com.cube.lush.player.mobile.content.adapter.ContentAdapter;
import com.cube.lush.player.mobile.events.EventTabSelection;
import com.cube.lush.player.mobile.events.EventTab;
import com.lush.lib.listener.OnListItemClickListener;
import com.lush.view.holder.BaseViewHolder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jamie Cruwys.
 */
public class EventViewHolder extends BaseViewHolder<EventTab>
{
	@BindView(R.id.section_title) public TextView title;
	@BindView(R.id.event_recycler) public RecyclerView eventRecycler;
	@BindView(R.id.event_more) public Button moreButton;

	private final List<MediaContent> items;
	private final OnListItemClickListener<MediaContent> itemListener;
	private final EventTabSelection tabListener;

	public EventViewHolder(@NonNull View view, @NonNull List<MediaContent> items, @NonNull OnListItemClickListener<MediaContent> itemListener, @NonNull EventTabSelection tabListener)
	{
		super(view);
		this.items = items;
		this.itemListener = itemListener;
		this.tabListener = tabListener;
		ButterKnife.bind(this, view);
	}

	@Override public void bind(final EventTab eventTab)
	{
		title.setText(eventTab.getDisplayName());

		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(eventRecycler.getContext(), LinearLayoutManager.HORIZONTAL, false);
		eventRecycler.setLayoutManager(linearLayoutManager);

		List<MediaContent> eventMediaContent = MediaManager.getInstance().filterContentByTag(eventTab.getTag(), items);

		ContentAdapter contentAdapter = new ContentAdapter(eventMediaContent, itemListener);
		eventRecycler.setAdapter(contentAdapter);

		moreButton.setOnClickListener(new View.OnClickListener()
		{
			@Override public void onClick(View v)
			{
				tabListener.selectTab(eventTab);
			}
		});
	}
}