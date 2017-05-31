package com.cube.lush.player.content.repository;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.cube.lush.player.api.LushAPI;
import com.cube.lush.player.content.handler.ResponseHandler;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import lombok.Getter;

/**
 * Created by Jamie Cruwys on 26/05/2017.
 */
public abstract class Repository<T>
{
	public static final String TAG = Repository.class.getSimpleName();

	public static final int SECOND = 1000;
	public static final int MINUTE = 60 * SECOND;
	public static final int HOUR = 60 * MINUTE;
	public static final int DAY = 24 * HOUR;

	private Set<T> items = new HashSet<>();
	@Getter private Set<T> newItems = new HashSet<>();
	private long lastRequestTime = 0;

	@Inject protected LushAPI api;

	public interface ItemRetrieval<T>
	{
		void onItemsRetrieved(Set<T> items);
	}

	/**
	 * Get items from the network and callback using the response handler
	 * @param callback to use to provide the results of the network request
	 * @return a Set<T> of the results. This can be empty, but not null.
	 */
	abstract @NonNull Set<T> getItemsFromNetwork(@NonNull ResponseHandler<T> callback);

	/**
	 * Provide the cache expiry time using the constants in {@link Repository}
	 * @return an int representing the cache expiry time in milliseconds
	 */
	public @IntRange(from=0) int getCacheExpiryTime()
	{
		return 5 * MINUTE;
	}

	/**
	 * Get items from either the cache, or make a network request if the cache has expired.
	 * @return {@link Set<T>} of items which can be empty, but not null.
	 */
	public void getItems(@NonNull final ItemRetrieval<T> callback)
	{
		if (cacheOutdated())
		{
			getItemsFromNetwork(new ResponseHandler<T>()
			{
				/**
				 * When the items have come back from the network
				 * @param latestItems {@link List<T>} that have come back from the network. This can be empty, but not null.
				 */
				@Override public void onSuccess(@NonNull List<T> latestItems)
				{
					updateItems(latestItems);
					callback.onItemsRetrieved(items);
				}

				/**
				 * When the network request fails
				 * @param t {@link Throwable} for the failure
				 */
				@Override public void onFailure(@Nullable Throwable t)
				{
					Log.e(TAG, "Repository request failed with reason:" + t);
				}
			});
		}
		else
		{
			callback.onItemsRetrieved(items);
		}
	}

	/**
	 * Works out if the cache is outdated
	 * @return true if the cache is outdated, false if it is not.
	 */
	private boolean cacheOutdated()
	{
		return System.currentTimeMillis() - lastRequestTime < getCacheExpiryTime();
	}

	/**
	 * Update the items
	 * @param latestItems to update
	 */
	protected void updateItems(@NonNull List<T> latestItems)
	{
		items.addAll(latestItems);

		newItems = new HashSet<>(items);
		newItems.removeAll(latestItems);
	}

	/**
	 * Is this item new?
	 * @param item that we want to check if it is new
	 * @return true if the item is new, false if it is not new
	 */
	public boolean isNew(@NonNull T item)
	{
		return items.contains(item);
	}
}