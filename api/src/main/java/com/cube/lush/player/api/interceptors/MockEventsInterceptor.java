package com.cube.lush.player.api.interceptors;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by Jamie Cruwys of 3 SIDED CUBE on 31/05/2017.
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
