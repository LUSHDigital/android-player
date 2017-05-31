package com.cube.lush.player.api;

import com.cube.lush.player.api.model.Channel;
import com.cube.lush.player.api.model.Event;
import com.cube.lush.player.api.model.LivePlaylist;
import com.cube.lush.player.api.model.Programme;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Defines Lush API interface using Retrofit.
 *
 * @author Jamie Cruwys
 */
public interface LushAPI
{
	/**
	 * Get a list of the channels
	 * @return channels
	 */
	@GET("channels")
	Call<List<Channel>> getChannels();

	/**
	 * Get programmes for a channel
	 * @param channelTag to get the programmes for
	 * @return programmes
	 */
	@GET("channels/{channel_tag}")
	Call<List<Programme>> getChannelProgrammes(@Path("channel_tag") String channelTag);

	/**
	 * Gets a list of the events
	 * @return events
	 */
	@GET("events")
	Call<List<Event>> getEvents();

	/**
	 * Gets programmes for a event
	 * @param eventTag to get the programmes for
	 * @return programmes
	 */
	@GET("events/{event_tag}")
	Call<List<Programme>> getEventProgrammes(@Path("event_tag") String eventTag);

	/**
	 * Gets programmes for a tag
	 * @param tag to get the programmes for
	 * @return programmes
	 */
	@GET("tags/{tag}")
	Call<List<Programme>> getProgrammesForTag(@Path("tag") String tag);

	/**
	 * Performs a text search and returns up to six results
	 * @param searchTerms, which supports multiple strings if they are separated by at + symbol
	 * @return results
	 */
	@GET("programme-search/{search_terms}")
	Call<List<Programme>> search(@Path("search_terms") String searchTerms);

	/**
	 * Gets the live playlist, which contains the live playlist id, or empty for the given timezone offset
	 * @param offset for the timezone, in the format "x minutes"
	 * @return live playlist content
	 */
	@GET("views/playlist")
	Call<List<LivePlaylist>> getLivePlaylist(@Query("offset") String offset);
}