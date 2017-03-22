package com.cube.lush.player.api;

import com.cube.lush.player.api.model.ContentType;
import com.cube.lush.player.api.model.RadioContent;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Radio Content local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class RadioContentUnitTest
{
	@Test public void radio_content_is_radio_type() throws Exception
	{
		RadioContent radioContent = new RadioContent();

		assertNotNull(radioContent);
		assertEquals(ContentType.RADIO, radioContent.getType());
	}
}
