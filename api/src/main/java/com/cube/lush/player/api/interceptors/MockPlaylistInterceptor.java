package com.cube.lush.player.api.interceptors;

import android.content.Context;
import android.support.annotation.NonNull;

import com.cube.lush.player.api.interceptors.base.BaseMockInterceptor;

/**
 * Created by Jamie Cruwys of 3 SIDED CUBE on 29/03/2017.
 */
public class MockPlaylistInterceptor extends BaseMockInterceptor
{
	public MockPlaylistInterceptor(@NonNull Context context)
	{
		super(context);
	}

	@Override protected String provideEndpointName()
	{
		return "playlist";
	}

	@Override protected String provideJsonFileName()
	{
		return "mock/api/playlist.json";
	}
}