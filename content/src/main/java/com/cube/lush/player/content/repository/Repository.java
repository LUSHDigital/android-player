package com.cube.lush.player.content.repository;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.lush.player.api.LushAPI;
import com.cube.lush.player.content.handler.ResponseHandler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Getter;

/**
 * Repository designed to be able to allow you to cache, know which content is new and get the data from the network
 *
 * @author Jamie Cruwys
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

	protected LushAPI api;

	public interface ItemRetrieval<T>
	{
		void onItemsRetrieved(@NonNull Set<T> items);
		void onItemRetrievalFailed(@NonNull Throwable throwable);
	}

	public Repository()
	{
		api = APIManager.INSTANCE.getAPI();
	}

	/**
	 * Get items from the network and callback using the response handler
	 * @param callback to use to provide the results of the network request
	 */
	abstract void getItemsFromNetwork(@NonNull ResponseHandler<T> callback);

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
	public void getItems(@NonNull final ResponseHandler<T> callback)
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
					callback.onSuccess(latestItems);
				}

				/**
				 * When the network request fails
				 * @param t {@link Throwable} for the failure
				 */
				@Override public void onFailure(@Nullable Throwable t)
				{
					callback.onFailure(t);
				}
			});
		}
		else
		{
			List cachedItems = new ArrayList<>();
			cachedItems.addAll(items);

			callback.onSuccess(cachedItems);
		}
	}

	/**
	 * Works out if the cache is outdated
	 * @return true if the cache is outdated, false if it is not.
	 */
	private boolean cacheOutdated()
	{
		return System.currentTimeMillis() > (lastRequestTime + getCacheExpiryTime());
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

	/**
	 * Mark the item as watched and remove it from the new items
	 * @param item that we have just watched
	 */
	public void watched(@NonNull T item)
	{
		newItems.remove(item);
	}
}