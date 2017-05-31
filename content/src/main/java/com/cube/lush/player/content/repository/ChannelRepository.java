package com.cube.lush.player.content.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.cube.lush.player.api.LushAPI;
import com.cube.lush.player.api.model.MediaContent;
import com.cube.lush.player.content.handler.ResponseHandler;
import com.cube.lush.player.content.model.Channel;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jamie Cruwys of 3 SIDED CUBE on 26/05/2017.
 */
public class ChannelRepository extends Repository<Channel>
{
	public static final ChannelRepository INSTANCE = new ChannelRepository();

	public void getChannel(@NonNull String name)
	{
		getItems(new ItemRetrieval<Channel>()
		{
			@Override public void onItemsRetrieved(Set<Channel> cacheItems)
			{
				for (Channel channel : cacheItems)
				{
					channel.getTitle()
				}
			}
		});
	}

//	public void getChannel(@NonNull String name, @NonNull ItemRetrieval<Channel> callback)
//	{
//		getItems(new ItemRetrieval<Channel>()
//		{
//			@Override public void onItemsRetrieved(Set<Channel> items)
//			{
//
//			}
//		});
//	}

	@NonNull @Override Set<Channel> getItemsFromNetwork(@NonNull ResponseHandler<Channel> callback)
	{
		api.getCategories()


		Call<List<MediaContent>> channelCall = api.getCategories(channelId, contentTypeName);



		String contentTypeName = contentType == null ? null : contentType.getApiContentType();
		Call<List<MediaContent>> channelCall = api.getCategories(channelId, contentTypeName);

		channelCall.enqueue(new Callback<List<MediaContent>>()
		{
			@Override
			public void onResponse(@NonNull final Call<List<MediaContent>> call, @NonNull final Response<List<MediaContent>> channelResponse)
			{
				if (!channelResponse.isSuccessful())
				{
					handler.onFailure(null);
				}
				else
				{
					List<MediaContent> channels = channelResponse.body();

					if (channels == null)
					{
						channels = Collections.emptyList();
					}

					handler.onSuccess(channels);
				}
			}

			@Override
			public void onFailure(@Nullable final Call<List<MediaContent>> call, @Nullable final Throwable t)
			{
				handler.onFailure(t);
			}
		});










		return null;
	}
}