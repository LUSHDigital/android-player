package com.lush.player.api.interceptor;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Interceptor for getting programmmes for a channel
 *
 * @author Jamie Cruwys
 */
public class MockChannelProgrammesInterceptor extends BaseMockInterceptor
{
	public MockChannelProgrammesInterceptor(@NonNull Context context)
	{
		super(context);
	}

	@Override protected String provideEndpointName()
	{
		return "channels/";
	}

	@Override protected String provideJsonFileName()
	{
		return "mock/api/programmes.json";
	}
}