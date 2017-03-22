package com.cube.lush.player.api;

import com.cube.lush.player.api.model.MediaContent;
import com.cube.lush.player.api.model.Programme;
import com.cube.lush.player.api.model.RadioContent;
import com.cube.lush.player.api.model.SearchResult;
import com.cube.lush.player.api.model.VideoContent;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Defines Lush API interface using Retrofit.
 *
 * @author Jamie Cruwys
 * @project lush-player-android-client
 */
public interface LushAPI
{
	/**
	 * Retrieves media items belonging to the specified channel ID, and of the specified content type.
	 *
	 * @param channel
	 * 				Corresponding to the ID of an item in {@link com.cube.lush.player.api.model.Channel}.
	 * @param contentType
	 * 				Corresponding to the name of an item in {@link com.cube.lush.player.api.model.CategoryContentType}.
	 * @return
	 */
	@GET("categories")
	Call<List<MediaContent>> getCategories(@Query("channel") String channel, @Query("type") String contentType);

	/**
	 * Retrieves the current live playlist ID for the given timezone offset
	 *
	 * @param offset
	 * 				In the format "x minutes"
	 * @return
	 */
	@GET("playlist")
	Call<List<MediaContent>> getPlaylist(@Query("offset") String offset);

	/**
	 * Retrieves videos recently added to the Lush content database and returns up to fifty results.
	 *
	 * @return
	 */
	@GET("videos")
	Call<List<VideoContent>> getVideos();

	/**
	 * Retrieves radio shows recently added to the Lush content database and returns up to fifty results.
	 *
	 * @return
	 */
	@GET("radio")
	Call<List<RadioContent>> getRadios();

	/**
	 * Performs a text search on the Lush content database and returns up to six results.
	 *
	 * @return
	 */
	@GET("search")
	Call<List<SearchResult>> search(@Query("title") String searchTerm);

	/**
	 * Retrieves more details about the programme with the specified ID.
	 *
	 * @param programmeId
	 * @return
	 */
	@GET("programme")
	Call<List<Programme>> getProgramme(@Query("id") String programmeId);
}
