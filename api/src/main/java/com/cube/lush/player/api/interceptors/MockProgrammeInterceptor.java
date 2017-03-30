package com.cube.lush.player.api.interceptors;

import android.content.Context;
import android.support.annotation.NonNull;

import com.cube.lush.player.api.interceptors.base.BaseMockInterceptor;

/**
 * Created by Jamie Cruwys of 3 SIDED CUBE on 29/03/2017.
 */
public class MockProgrammeInterceptor extends BaseMockInterceptor
{
	public MockProgrammeInterceptor(@NonNull Context context)
	{
		super(context);
	}

	@Override protected String provideEndpointName()
	{
		return "programme";
	}

	@Override protected String provideJsonFileName()
	{
		return "mock/api/programme.json";
	}
}