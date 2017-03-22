package com.cube.lush.player.api;

import com.cube.lush.player.api.model.ContentType;
import com.cube.lush.player.api.model.VideoContent;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Video Content local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class VideoContentUnitTest
{
	@Test public void video_content_is_tv_type() throws Exception
	{
		VideoContent videoContent = new VideoContent();

		assertNotNull(videoContent);
		assertEquals(ContentType.TV, videoContent.getType());
	}
}