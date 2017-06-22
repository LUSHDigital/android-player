package com.lush.player;

import com.lush.player.api.model.Tag;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Tag model logic unit test
 *
 * @author Jamie Cruwys
 */
public class TagUnitTest
{
	private Tag tag;

	@Before
	public void setUp() throws Exception
	{
		tag = new Tag();
		tag.setTag("Lush Life");
	}

	@Test
	public void retains_existing_hashtag() throws Exception
	{
		tag.setName("#LushLife");
		assertEquals("#LushLife", tag.getName());
	}

	@Test public void adds_missing_hashtag() throws Exception
	{
		tag.setName("LushLife");
		assertEquals("#LushLife", tag.getName());
	}

	@Test public void trims_space_and_retains_existing_hastag() throws Exception
	{
		tag.setName("    #LushLife");
		assertEquals("#LushLife", tag.getName());
	}

	@Test public void trims_space_and_adds_missing_hashtag() throws Exception
	{
		tag.setName("    LushLife");
		assertEquals("#LushLife", tag.getName());
	}
}