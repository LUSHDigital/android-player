package com.cube.lush.player.api.interceptors;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by Jamie Cruwys of 3 SIDED CUBE on 31/05/2017.
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