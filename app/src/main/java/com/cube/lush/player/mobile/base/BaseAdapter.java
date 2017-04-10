package com.cube.lush.player.mobile.base;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jamie Cruwys of 3 SIDED CUBE on 04/04/2017.
 */
public abstract class BaseAdapter<ITEMTYPE, VIEWHOLDER extends BaseViewHolder<ITEMTYPE>> extends RecyclerView.Adapter<VIEWHOLDER>
{
	/**
	 * Provide the layout for the view holder
	 * @return the layout resource
	 */
	@LayoutRes protected abstract int provideViewHolderLayout();

	/**
	 * Create the view holder
	 * @param itemView of the view holder layout that has been inflated
	 * @return the created view holder
	 */
	@NonNull protected abstract VIEWHOLDER createViewHolder(@NonNull View itemView);

	/**
	 * Bind the item to the view holder
	 * @param holder view holder
	 * @param item for the holder's position in the list
	 */
	protected abstract void bind(@NonNull VIEWHOLDER holder, @NonNull ITEMTYPE item);

	/**
	 * Items that will back this adapter
	 */
	@NonNull private List<ITEMTYPE> items = new ArrayList<ITEMTYPE>();

	/**
	 * Basic constructor that takes the list items and the callback to make state changes.
	 * You can provide an empty list.
	 * @param items that can be empty, but not null
	 */
	public BaseAdapter(@NonNull List<ITEMTYPE> items)
	{
		this.items = items;
	}

	@Override public VIEWHOLDER onCreateViewHolder(ViewGroup parent, int viewType)
	{
		View itemView = LayoutInflater.from(parent.getContext()).inflate(provideViewHolderLayout(), parent, false);
		return createViewHolder(itemView);
	}

	@Override public void onBindViewHolder(VIEWHOLDER holder, int position)
	{
		ITEMTYPE item = items.get(position);
		holder.setItem(item);

		bind(holder, item);
	}

	@Override public int getItemCount()
	{
		return items.size();
	}
}