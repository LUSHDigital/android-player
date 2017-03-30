package com.cube.lush.player.api.interceptors.channels;

import android.content.Context;
import android.support.annotation.NonNull;

import com.cube.lush.player.api.interceptors.base.BaseChannelsMockInterceptor;

/**
 * Created by Jamie Cruwys of 3 SIDED CUBE on 30/03/2017.
 */
public class MockGorillaChannelInterceptor extends BaseChannelsMockInterceptor
{
	public MockGorillaChannelInterceptor(@NonNull Context context)
	{
		super(context);
	}

	@Override protected String provideJsonFileName()
	{
		return "mock/api/channels/gorilla.json";
	}

	@Override protected String provideChannelName()
	{
		return "gorilla";
	}
}