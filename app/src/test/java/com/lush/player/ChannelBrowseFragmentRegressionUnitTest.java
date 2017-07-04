package com.lush.player;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;

/**
 * <Class Description>
 *
 * @author Jamie Cruwys
 */
@RunWith(MockitoJUnitRunner.class)
public class ChannelBrowseFragmentRegressionUnitTest
{
	private MockChannelBrowseFragment fragment;

	@Test
	public void null_channel_causing_null_pointer_regression()
	{
		fragment = new MockChannelBrowseFragment();
		fragment.channel = null;

		// Fetch data should cause a crash by now if the regression exists
		fragment.fetchData();

		// If it didn't crash then mark the test as successful
		assertTrue(true);
	}
}