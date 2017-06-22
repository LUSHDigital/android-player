package com.lush.player.api.interceptor;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Interceptor for getting programmmes for a tag
 *
 * @author Jamie Cruwys
 */
public class MockTagProgrammesInterceptor extends BaseMockInterceptor
{
	public MockTagProgrammesInterceptor(@NonNull Context context)
	{
		super(context);
	}

	@Override protected String provideEndpointName()
	{
		return "tags/";
	}

	@Override protected String provideJsonFileName()
	{
		return "mock/api/programmes.json";
	}
}