package com.cube.lush.player.mobile.events.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cube.lush.player.R;
import com.cube.lush.player.mobile.base.BaseViewHolder;
import com.cube.lush.player.mobile.base.RecyclerViewClickedListener;
import com.cube.lush.player.mobile.events.EventTab;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jamie Cruwys of 3 SIDED CUBE on 13/04/2017.
 */
public class AllEventViewHolder extends BaseViewHolder<EventTab>
{
	@BindView(R.id.section_title) public TextView title;
	@BindView(R.id.event_recycler) public RecyclerView eventRecycler;
	@BindView(R.id.event_more) public Button moreButton;

	public AllEventViewHolder(@NonNull View itemView, @NonNull RecyclerViewClickedListener<EventTab> listener)
	{
		super(itemView, listener);
		ButterKnife.bind(this, itemView);
	}
}