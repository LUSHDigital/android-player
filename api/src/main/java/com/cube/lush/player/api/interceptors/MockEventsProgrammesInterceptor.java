package com.cube.lush.player.api.interceptors;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Interceptor for getting programmmes for an event
 *
 * @author Jamie Cruwys
 */
public class MockEventsProgrammesInterceptor extends BaseMockInterceptor
{
	public MockEventsProgrammesInterceptor(@NonNull Context context)
	{
		super(context);
	}

	@Override protected String provideEndpointName()
	{
		return "events/";
	}

	@Override protected String provideJsonFileName()
	{
		return "mock/api/programmes.json";
	}
}