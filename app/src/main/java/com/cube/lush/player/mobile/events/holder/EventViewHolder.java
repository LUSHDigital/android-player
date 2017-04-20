package com.cube.lush.player.mobile.events.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cube.lush.player.R;
import com.cube.lush.player.api.model.MediaContent;
import com.cube.lush.player.content.manager.MediaManager;
import com.cube.lush.player.mobile.content.adapter.ContentAdapter;
import com.cube.lush.player.mobile.events.EventTab;
import com.cube.lush.player.mobile.events.EventTabSelection;
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
	@BindView(R.id.indicator_container) public LinearLayout indicatorContainer;

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

		final int MAX_HORIZONTAL_ITEMS = title.getContext().getResources().getInteger(R.integer.paging_max_items);
		final int PAGE_SIZE = title.getContext().getResources().getInteger(R.integer.paging_page_size);

		List<MediaContent> eventMediaContent = MediaManager.getInstance().filterContentByTag(eventTab.getTag(), items, MAX_HORIZONTAL_ITEMS);

		indicatorContainer.removeAllViews();

		for (int index = 0; index < eventMediaContent.size(); index++)
		{
			View view = LayoutInflater.from(indicatorContainer.getContext()).inflate(R.layout.page_indicator, indicatorContainer, false);
			final int finalIndex = index;
			view.setOnClickListener(new View.OnClickListener()
			{
				@Override public void onClick(View v)
				{
					eventRecycler.scrollToPosition(finalIndex);
				}
			});
			indicatorContainer.addView(view);
		}

		// TODO: Use PAGE_SIZE
		final LinearLayoutManager layoutManager = new LinearLayoutManager(eventRecycler.getContext(), LinearLayoutManager.HORIZONTAL, false);
		eventRecycler.setLayoutManager(layoutManager);

		eventRecycler.addOnScrollListener(new RecyclerView.OnScrollListener()
		{
			@Override public void onScrolled(RecyclerView recyclerView, int dx, int dy)
			{
				super.onScrolled(recyclerView, dx, dy);
				int firstVisisbleItem = layoutManager.findFirstVisibleItemPosition();

				Log.e("POS", "pos=" + firstVisisbleItem);

				if (firstVisisbleItem < 0 || firstVisisbleItem > indicatorContainer.getChildCount() - 1)
				{
					return;
				}

				for (int index = 0; index < indicatorContainer.getChildCount(); index++)
				{
					View child = indicatorContainer.getChildAt(index);
					child.setEnabled(false);
				}

				indicatorContainer.getChildAt(firstVisisbleItem).setEnabled(true);
			}
		});

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