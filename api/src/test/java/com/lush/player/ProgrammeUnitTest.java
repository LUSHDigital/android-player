package com.lush.player;

import com.lush.player.api.model.ContentType;
import com.lush.player.api.model.Programme;
import com.lush.player.api.model.Tag;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Programme model logic unit test
 *
 * @author Jamie Cruwys
 */
public class ProgrammeUnitTest
{
	private Programme programme;

	@Before
	public void setUp() throws Exception
	{
		programme = new Programme();
		programme.setTitle("The Lush Speakeasy | Not Knowing");
		programme.setId("71d523e6-b580-4cf8-b768-b78086d3b8a7");
		programme.setDate(new Date("30/05/2017"));
		programme.setAlias("lush-speakeasy-not-knowing");
		programme.setDescription("Welcome to the Lush Speakeasy, a series of conversations that aims to create a temporary space where a small group of us can essentially hang out and discuss a subject. Not a historical event or a famous figure from the past but a word or a short phrase. Charlie Moores hosts 'Not Knowing' with guests Sarah Corbett (Craftivist Collective), David Pearl (Street Wisdom), and Mark Pilkington (Mirage Men).\n");
		programme.setThumbnail("http://res.cloudinary.com/lush/image/upload/v1496138120/Not%20Knowing%20Cover.jpg");
		programme.setChannel("lushlife");
		programme.setEvent(null);

		ArrayList<Tag> tags = new ArrayList<>();

		Tag tag = new Tag();
		tag.setTag("speakeasy_sarah_corbett_david_pearl_mark_pilkington_not_knowing_andrew_paine_charlie_moores_crafitivism_street_wisdom_mirage_men");
		tag.setName("#speakeasy #sarah corbett #david pearl #mark pilkington #not knowing #andrew paine #charlie moores #crafitivism #street wisdom #mirage men");

		tags.add(tag);

		programme.setTags(tags);
		programme.setType(ContentType.RADIO);
		programme.setFile("http://res.cloudinary.com/lush/raw/upload/v1496137851/Not%20Knowing.mp3");
		//programme.setDuration("38:48");
	}

	@Test
	public void date_time_doesnt_return_future_date() throws Exception
	{
		long now = System.currentTimeMillis();
		long dayLater = now + 86400000;

		Date date = new Date(dayLater);
		programme.setDate(date);

		long dateTime = programme.getDateTime();

		assertEquals(now, dateTime);
	}

	@Test
	public void web_link_points_to_website() throws Exception
	{
		assertTrue(programme.getWebLink().startsWith("http://player.lush.com/tv"));
	}
}