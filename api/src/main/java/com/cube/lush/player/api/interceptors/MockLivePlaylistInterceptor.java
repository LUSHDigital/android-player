package com.cube.lush.player.api.interceptors;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by Jamie Cruwys of 3 SIDED CUBE on 29/03/2017.
 */
public class MockLivePlaylistInterceptor extends BaseMockInterceptor
{
	public MockLivePlaylistInterceptor(@NonNull Context context)
	{
		super(context);
	}

	@Override protected String provideEndpointName()
	{
		return "views/playlist";
	}

	@Override protected String provideJsonFileName()
	{
		return "mock/api/playlists.json";
	}
}