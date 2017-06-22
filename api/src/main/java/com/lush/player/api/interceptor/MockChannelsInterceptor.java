package com.lush.player.api.interceptor;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Interceptor for getting all channels
 *
 * @author Jamie Cruwys
 */
public class MockChannelsInterceptor extends BaseMockInterceptor
{
	public MockChannelsInterceptor(@NonNull Context context)
	{
		super(context);
	}

	@Override protected String provideEndpointName()
	{
		return "channels";
	}

	@Override protected String provideJsonFileName()
	{
		return "mock/api/channels.json";
	}
}