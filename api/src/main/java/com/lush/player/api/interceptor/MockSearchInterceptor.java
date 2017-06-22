package com.lush.player.api.interceptor;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Interceptor for search
 *
 * @author Jamie Cruwys
 */
public class MockSearchInterceptor extends BaseMockInterceptor
{
	public MockSearchInterceptor(@NonNull Context context)
	{
		super(context);
	}

	@Override protected String provideEndpointName()
	{
		return "programme-search/";
	}

	@Override protected String provideJsonFileName()
	{
		return "mock/api/programmes.json";
	}
}
