package com.cube.lush.player.mobile.events.holder;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cube.lush.player.R;
import com.cube.lush.player.content.handler.ResponseHandler;
import com.cube.lush.player.content.repository.EventProgrammesRepository;
import com.cube.lush.player.content.util.MediaSorter;
import com.cube.lush.player.mobile.content.adapter.ContentCarouselAdapter;
import com.cube.lush.player.mobile.events.EventTabSelection;
import com.lush.lib.listener.OnListItemClickListener;
import com.lush.player.api.model.Event;
import com.lush.player.api.model.Programme;
import com.lush.view.holder.BaseViewHolder;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Event View Holder
 *
 * @author Jamie Cruwys
 */
public class EventViewHolder extends BaseViewHolder<Event>
{
	@BindView(R.id.section_title) public TextView title;
	@BindView(R.id.event_recycler) public RecyclerView eventRecycler;
	@BindView(R.id.event_more) public Button moreButton;
	@BindView(R.id.indicator_container) public LinearLayout indicatorContainer;

	private final List<Programme> items;
	private final OnListItemClickListener<Programme> itemListener;
	private final EventTabSelection tabListener;

	public EventViewHolder(@NonNull View view, @NonNull List<Programme> items, @NonNull OnListItemClickListener<Programme> itemListener, @NonNull EventTabSelection tabListener)
	{
		super(view);
		this.items = items;
		this.itemListener = itemListener;
		this.tabListener = tabListener;
		ButterKnife.bind(this, view);
	}

	@Override public void bind(final Event event)
	{
		title.setText(event.getName());

		final int MAX_HORIZONTAL_ITEMS = title.getContext().getResources().getInteger(R.integer.paging_max_items);
		final int PAGE_SIZE = title.getContext().getResources().getInteger(R.integer.paging_page_size);

		EventProgrammesRepository.getInstance(title.getContext()).setEventTag(event.getTag());
		EventProgrammesRepository.getInstance(title.getContext()).getItems(new ResponseHandler<Programme>()
		{
			@Override public void onSuccess(@NonNull List<Programme> items)
			{
				int toIndex = MAX_HORIZONTAL_ITEMS;

				if (items.isEmpty())
				{
					toIndex = 0;
				}
				else if (items.size() < MAX_HORIZONTAL_ITEMS)
				{
					toIndex = items.size();
				}

				items = items.subList(0, toIndex);

				MediaSorter.MOST_RECENT_FIRST.sort(items);
				bindProgrammes(items);
			}

			@Override public void onFailure(@Nullable Throwable t)
			{
				bindProgrammes(Collections.EMPTY_LIST);
			}
		});

		indicatorContainer.removeAllViews();

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

		moreButton.setOnClickListener(new View.OnClickListener()
		{
			@Override public void onClick(View v)
			{
				tabListener.selectTab(event);
			}
		});
	}

	private void bindProgrammes(@NonNull List<Programme> programmes)
	{
		for (int index = 0; index < programmes.size(); index++)
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

		ContentCarouselAdapter contentAdapter = new ContentCarouselAdapter(programmes, itemListener);
		eventRecycler.setAdapter(contentAdapter);
	}
}