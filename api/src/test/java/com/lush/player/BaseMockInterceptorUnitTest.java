package com.lush.player;

import android.test.mock.MockContext;

import com.lush.player.api.API;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import okhttp3.Request;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * <Class Description>
 *
 * @author Jamie Cruwys
 */
@RunWith(MockitoJUnitRunner.class)
public class BaseMockInterceptorUnitTest
{
	private MockInterceptor interceptor;
	private Request request;

	@Mock
	MockContext context;

	@Before
	public void setUp() throws Exception
	{
		API.INSTANCE.initialise(context);
		API.INSTANCE.setMocked(false);

		interceptor = new MockInterceptor(context);

		request = new Request.Builder()
			.method("GET", null)
			.url("http://admin.player.lush.com/lushtvapi/v2/channels")
			.build();
	}

	@Test
	public void doesnt_mock_when_flag_is_false() throws Exception
	{
		API.INSTANCE.setMocked(false);
		boolean shouldMock = interceptor.shouldMockResponse(request);
		assertFalse(shouldMock);
	}

	@Test
	public void mocks_when_flag_is_true() throws Exception
	{
		API.INSTANCE.setMocked(true);
		boolean shouldMock = interceptor.shouldMockResponse(request);
		assertTrue(shouldMock);
	}
}