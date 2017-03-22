package com.cube.lush.player.api;

import com.cube.lush.player.api.model.ContentType;
import com.cube.lush.player.api.model.RadioContent;
import com.cube.lush.player.api.model.SearchResult;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Search Result local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class SearchResultUnitTest
{
	private static final String RADIO_THUMBNAIL = "radio_thumbnail";
	private static final String VIDEO_THUMBNAIL = "video_thumbnail";
	private SearchResult searchResult;

	@Before public void setUp() throws Exception
	{
		searchResult = new SearchResult();
	}

	@Test public void tv_thumbnail_only_shows_video_thumbnail() throws Exception
	{
		searchResult.setType(ContentType.TV);

		assertNull(searchResult.getThumbnail());

		searchResult.setRadioThumbnail(RADIO_THUMBNAIL);
		assertNull(searchResult.getThumbnail());

		searchResult.setVideoThumbnail(VIDEO_THUMBNAIL);
		assertEquals(VIDEO_THUMBNAIL, searchResult.getThumbnail());

		searchResult = new SearchResult();
		searchResult.setType(ContentType.TV);
		searchResult.setVideoThumbnail(VIDEO_THUMBNAIL);
		assertEquals(VIDEO_THUMBNAIL, searchResult.getThumbnail());
	}

	@Test public void radio_thumbnail_only_shows_radio_thumbnail() throws Exception
	{
		searchResult.setType(ContentType.RADIO);

		assertNull(searchResult.getThumbnail());

		searchResult.setVideoThumbnail(VIDEO_THUMBNAIL);
		assertNull(searchResult.getThumbnail());

		searchResult.setRadioThumbnail(RADIO_THUMBNAIL);
		assertEquals(RADIO_THUMBNAIL, searchResult.getThumbnail());

		searchResult = new SearchResult();
		searchResult.setType(ContentType.RADIO);
		searchResult.setRadioThumbnail(RADIO_THUMBNAIL);
		assertEquals(RADIO_THUMBNAIL, searchResult.getThumbnail());
	}

	@Test public void thumbnail_with_no_type_uses_any_available_thumbnail() throws Exception
	{
		assertNull(searchResult.getThumbnail());

		searchResult.setRadioThumbnail(RADIO_THUMBNAIL);
		assertEquals(RADIO_THUMBNAIL, searchResult.getThumbnail());

		searchResult.setVideoThumbnail(VIDEO_THUMBNAIL);
		assertEquals(VIDEO_THUMBNAIL, searchResult.getThumbnail());
	}
}