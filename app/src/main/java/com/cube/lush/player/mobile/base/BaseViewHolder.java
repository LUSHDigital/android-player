package com.cube.lush.player.mobile.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * View holder that supports item clicked functionality
 * @param <ITEMTYPE>
 */
public class BaseViewHolder<ITEMTYPE> extends RecyclerView.ViewHolder implements View.OnClickListener
{
	private ITEMTYPE item;
	private RecyclerViewClickedListener<ITEMTYPE> listener;

	public BaseViewHolder(@NonNull View itemView, @NonNull final RecyclerViewClickedListener<ITEMTYPE> listener)
	{
		super(itemView);
		this.listener = listener;
		itemView.setOnClickListener(this);
	}

	@Override
	public void onClick(View view)
	{
		listener.onRecyclerViewItemClicked(item);
	}

	public void setItem(@NonNull ITEMTYPE item)
	{
		this.item = item;
	}
}