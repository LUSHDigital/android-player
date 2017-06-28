package com.cube.lush.player.content.repository;

import android.content.Context;
import android.support.annotation.NonNull;

import com.cube.lush.player.content.handler.ResponseHandler;
import com.cube.lush.player.content.util.MediaSorter;
import com.google.gson.reflect.TypeToken;
import com.lush.player.api.model.Programme;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Latest Programmes Repository
 *
 * @author Jamie Cruwys
 */
public class LatestProgrammesRepository extends BaseProgrammeRepository
{
	private static LatestProgrammesRepository instance;

	public LatestProgrammesRepository(@NonNull Context context)
	{
		super(context);
	}

	public static LatestProgrammesRepository getInstance(@NonNull Context context)
	{
		if (instance == null)
		{
			instance = new LatestProgrammesRepository(context);
		}

		return instance;
	}

	@Override void getItemsFromNetwork(@NonNull final ResponseHandler<Programme> callback)
	{
		final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
		final List<Programme> composite = new ArrayList<>();

		// Only return once the two requests have completed
		final CountDownLatch countDownLatch = new CountDownLatch(2);

		Call<List<Programme>> videoArchive = api.getVideoArchive();
		videoArchive.enqueue(new Callback<List<Programme>>()
		{
			@Override public void onResponse(Call<List<Programme>> call, Response<List<Programme>> response)
			{
				readWriteLock.writeLock().lock();

				try
				{
					if (response != null && response.isSuccessful() && response.body() != null)
					{
						composite.addAll(response.body());
					}
				}
				finally
				{
					readWriteLock.writeLock().unlock();
					countDownLatch.countDown();

					if (countDownLatch.getCount() == 0)
					{
						if (composite.isEmpty())
						{
							callback.onFailure(null);
						}
						else
						{
							MediaSorter.MOST_RECENT_FIRST.sort(composite);
							callback.onSuccess(composite);
						}
					}
				}
			}

			@Override public void onFailure(Call<List<Programme>> call, Throwable t)
			{
				if (countDownLatch.getCount() == 0)
				{
					callback.onFailure(t);
				}
			}
		});

		Call<List<Programme>> radioArchive = api.getRadioArchive();
		radioArchive.enqueue(new Callback<List<Programme>>()
		{
			@Override public void onResponse(Call<List<Programme>> call, Response<List<Programme>> response)
			{
				readWriteLock.writeLock().lock();

				try
				{
					if (response != null && response.isSuccessful() && response.body() != null)
					{
						composite.addAll(response.body());
					}
				}
				finally
				{
					readWriteLock.writeLock().unlock();
					countDownLatch.countDown();

					if (countDownLatch.getCount() == 0)
					{
						if (composite.isEmpty())
						{
							callback.onFailure(null);
						}
						else
						{
							MediaSorter.MOST_RECENT_FIRST.sort(composite);
							callback.onSuccess(composite);
						}
					}
				}
			}

			@Override public void onFailure(Call<List<Programme>> call, Throwable t)
			{
				if (countDownLatch.getCount() == 0)
				{
					callback.onFailure(t);
				}
			}
		});
	}

	@Override
	protected TypeToken<List<Programme>> provideGsonTypeToken()
	{
		return new TypeToken<List<Programme>>(){};
	}

	@Override
	protected String providePreferenceName()
	{
		return "LatestProgrammes";
	}
}