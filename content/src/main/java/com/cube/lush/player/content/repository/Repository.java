package com.cube.lush.player.content.repository;

import android.support.annotation.NonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Getter;

/**
 * Created by Jamie Cruwys of 3 SIDED CUBE on 26/05/2017.
 */
public abstract class Repository<T>
{
	@Getter protected Set<T> items = new HashSet<>();
	@Getter protected Set<T> newItems = new HashSet<>();

	public void refresh()
	{
		
	}

	protected void update(@NonNull List<T> updatedItems)
	{
		items.addAll(updatedItems);

		newItems = new HashSet<>(items);
		newItems.removeAll(updatedItems);
	}

	public boolean isNew(@NonNull T item)
	{
		return items.contains(item);
	}
}