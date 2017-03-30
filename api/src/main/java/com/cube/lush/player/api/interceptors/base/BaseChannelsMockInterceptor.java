package com.cube.lush.player.api.interceptors.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import okhttp3.Request;

/**
 * Created by Jamie Cruwys of 3 SIDED CUBE on 30/03/2017.
 */
public abstract class BaseChannelsMockInterceptor extends BaseMockInterceptor
{
	public BaseChannelsMockInterceptor(@NonNull Context context)
	{
		super(context);
	}

	/**
	 * Provide the channel name you want to mock a response for
	 * @return String representing the channel name
	 */
	protected abstract String provideChannelName();

	@Override protected boolean shouldMockResponse(@NonNull Request originalRequest)
	{
		boolean shouldMock = super.shouldMockResponse(originalRequest);

		if (shouldMock)
		{
			String channel = originalRequest.url().queryParameter("channel");
			String channelToMock = provideChannelName();

			if (TextUtils.isEmpty(channel) || TextUtils.isEmpty(channelToMock) || !channel.equals(channelToMock))
			{
				shouldMock = false;
			}
		}

		return shouldMock;
	}

	@Override protected String provideEndpointName()
	{
		return "categories";
	}
}