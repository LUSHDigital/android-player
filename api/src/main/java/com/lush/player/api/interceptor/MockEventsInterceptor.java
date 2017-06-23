package com.lush.player.api.interceptor;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Interceptor for getting all events
 *
 * @author Jamie Cruwys
 */
public class MockEventsInterceptor extends BaseMockInterceptor
{
	public MockEventsInterceptor(@NonNull Context context)
	{
		super(context);
	}

	@Override protected String provideEndpointName()
	{
		return "events";
	}

	@Override protected String provideJsonFileName()
	{
		return "mock/api/events.json";
	}
}
