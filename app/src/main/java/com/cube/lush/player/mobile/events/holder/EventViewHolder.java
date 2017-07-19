package com.cube.lush.player.mobile.events.holder;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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

import java.util.ArrayList;
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

	private final OnListItemClickListener<Programme> itemListener;
	private final EventTabSelection tabListener;

	public EventViewHolder(@NonNull View view, @NonNull OnListItemClickListener<Programme> itemListener, @NonNull EventTabSelection tabListener)
	{
		super(view);
		this.itemListener = itemListener;
		this.tabListener = tabListener;
		ButterKnife.bind(this, view);
	}

	@Override public void bind(@NonNull final Event event)
	{
		title.setText(event.getName());

		final int MAX_HORIZONTAL_ITEMS = title.getContext().getResources().getInteger(R.integer.paging_max_items);

		if (event != null)
		{
			EventProgrammesRepository.getInstance(title.getContext()).setEventTag(event.getTag());
			EventProgrammesRepository.getInstance(title.getContext()).getItems(new ResponseHandler<Programme>()
			{
				@Override public void onSuccess(@NonNull List<Programme> items)
				{
					ArrayList<Programme> itemsWithMatchingTag = new ArrayList<Programme>();

					// Filter items by tag, due to caching keeping hold of all tagged content and not just for this event
					for (Programme programme : items)
					{
						if (programme == null)
						{
							continue;
						}

						String programmeEvent = programme.getEvent();

						if (programmeEvent == null || TextUtils.isEmpty(programmeEvent))
						{
							continue;
						}

						if (programmeEvent.equals(event.getTag()))
						{
							itemsWithMatchingTag.add(programme);
						}
					}

					items = itemsWithMatchingTag;

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
		}

		// TODO: Use PAGE_SIZE
		final LinearLayoutManager layoutManager = new LinearLayoutManager(eventRecycler.getContext(), LinearLayoutManager.HORIZONTAL, false);
		eventRecycler.setLayoutManager(layoutManager);

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
		ContentCarouselAdapter contentAdapter = new ContentCarouselAdapter(programmes, itemListener);
		eventRecycler.setAdapter(contentAdapter);
	}
}