package com.cube.lush.player.api.interceptors;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Interceptor for getting the live playlist
 *
 * @author Jamie Cruwys
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