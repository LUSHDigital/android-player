package com.cube.lush.player.api;

import com.cube.lush.player.model.MediaContent;
import com.cube.lush.player.model.Programme;
import com.cube.lush.player.model.RadioContent;
import com.cube.lush.player.model.SearchResult;
import com.cube.lush.player.model.VideoContent;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * @author Jamie Cruwys
 * @project lush-player-android-client
 */
public interface LushAPI
{
	@GET("categories")
	Call<List<MediaContent>> getCategories(@Header("channel") String channel, @Header("type") String contentType);

	@GET("playlist")
	Call<List<MediaContent>> getPlaylist(@Header("offset") String offset);

	@GET("videos")
	Call<List<VideoContent>> getVideos();

	@GET("radio")
	Call<List<RadioContent>> getRadios();

	@GET("search")
	Call<List<SearchResult>> search(@Query("title") String searchTerm);

	@GET("programme")
	Call<List<Programme>> getProgramme(@Query("id") String programmeId);
}
