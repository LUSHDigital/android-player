package com.cube.lush.player.content.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.cube.lush.player.content.handler.ResponseHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lush.player.api.LushAPI;

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
	public static final String PREFERENCE_CACHE_STORE = "RepositoryCache";

	public static final int SECOND = 1000;
	public static final int MINUTE = 60 * SECOND;
	public static final int HOUR = 60 * MINUTE;
	public static final int DAY = 24 * HOUR;

	private Set<T> items = new HashSet<>();
	@Getter private Set<T> newItems = new HashSet<>();
	private long lastRequestTime = 0;

	protected LushAPI api;
	protected Context context;

	public interface ItemRetrieval<T>
	{
		void onItemsRetrieved(@NonNull Set<T> items);
		void onItemRetrievalFailed(@NonNull Throwable throwable);
	}

	public Repository(@NonNull Context context)
	{
		this.context = context;
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
	 */
	public void getItems(@Nullable final ResponseHandler<T> callback)
	{
		if (isFirstUsage())
		{
			loadItemsFromDisk();
		}

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

					if (callback != null)
					{
						callback.onSuccess(latestItems);
					}
				}

				/**
				 * When the network request fails
				 * @param t {@link Throwable} for the failure
				 */
				@Override public void onFailure(@Nullable Throwable t)
				{
					if (items != null)
					{
						// Use cached content
						if (callback != null)
						{
							callback.onSuccess(getCachedItems());
						}

						Log.w(TAG, "Network unavailable, using cache");
					}
					else
					{
						if (callback != null)
						{
							callback.onFailure(t);
						}
					}
				}
			});
		}
		else
		{
			if (callback != null)
			{
				callback.onSuccess(getCachedItems());
			}
		}
	}

	private List<T> getCachedItems()
	{
		List<T> cachedItems = new ArrayList<>();
		cachedItems.addAll(items);

		return cachedItems;
	}

	private boolean isFirstUsage()
	{
		return items.isEmpty() && newItems.isEmpty() && lastRequestTime == 0;
	}

	/**
	 * Gets items from the cache synchronously, and requests new data if necessary so the next call has the correct dataset.
	 * This should only be used for edge cases. For most cases, you should use {@link #getItems(ResponseHandler)}
	 * @return {@link List<T>} of items which can be empty, but not null.
	 */
	public List<T> getItemsSynchronously()
	{
		ArrayList<T> cachedItems = new ArrayList<T>();
		cachedItems.addAll(items);

		if (cacheOutdated())
		{
			getItems(null);
		}

		return cachedItems;
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

		saveItemsToDisk(items);
	}

	private Gson gson;

	private void saveItemsToDisk(@Nullable Set<T> itemsToSave)
	{
		if (gson == null)
		{
			gson = new Gson();
		}

		String json = gson.toJson(itemsToSave);

		SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_CACHE_STORE, Context.MODE_PRIVATE);
		sharedPreferences.edit()
			.putString(providePreferenceName(), json)
			.apply();
	}

	private void loadItemsFromDisk()
	{
		SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_CACHE_STORE, Context.MODE_PRIVATE);
		String json = sharedPreferences.getString(providePreferenceName(), null);

		if (gson == null)
		{
			gson = new Gson();
		}

		List<T> itemsFromDisk = gson.fromJson(json, provideGsonTypeToken().getType());

		if (itemsFromDisk == null || itemsFromDisk.isEmpty())
		{
			items = new HashSet<T>();
		}
		else
		{
			items = new HashSet<T>(itemsFromDisk);
		}
	}

	protected abstract TypeToken<List<T>> provideGsonTypeToken();

	/**
	 * Provide the preference name to use to store cached data for this repository
	 * @return String for the
	 */
	protected abstract String providePreferenceName();

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