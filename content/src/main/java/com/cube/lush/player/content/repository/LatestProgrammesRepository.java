package com.cube.lush.player.content.repository;

import android.support.annotation.NonNull;

import com.cube.lush.player.api.model.Programme;
import com.cube.lush.player.content.handler.ResponseHandler;

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
	public static final LatestProgrammesRepository INSTANCE = new LatestProgrammesRepository();

	private LatestProgrammesRepository() { }

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
}