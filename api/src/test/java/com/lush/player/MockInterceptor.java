package com.lush.player;

import android.content.Context;
import android.support.annotation.NonNull;

import com.lush.player.api.interceptor.BaseMockInterceptor;

import okhttp3.Request;

/**
 * <Class Description>
 *
 * @author Jamie Cruwys
 */
public class MockInterceptor extends BaseMockInterceptor
{
	public MockInterceptor(@NonNull Context context)
	{
		super(context);
	}

	@Override
	protected String provideEndpointName()
	{
		return "channels";
	}

	@Override
	protected String provideJsonFileName()
	{
		return "mock/api/channels.json";
	}

	@Override
	public boolean shouldMockResponse(@NonNull Request originalRequest)
	{
		return super.shouldMockResponse(originalRequest);
	}

	@Override
	public String getResponse()
	{
		return super.getResponse();
	}
}