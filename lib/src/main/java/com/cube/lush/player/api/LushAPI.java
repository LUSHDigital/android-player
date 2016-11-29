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
	String TV_API_VERSION = "1";

	@GET("lushtvapi/v" + TV_API_VERSION + "/views/playlist")
	Call<List<MediaContent>> getChannel(@Header("channel") String channel);

	@GET("lushtvapi/v" + TV_API_VERSION + "/views/playlist")
	Call<List<MediaContent>> getPlaylist(@Header("offset") String offset);

	@GET("lushtvapi/v" + TV_API_VERSION + "/views/videos")
	Call<List<VideoContent>> listVideos();

	@GET("lushtvapi/v" + TV_API_VERSION + "/views/radio")
	Call<List<RadioContent>> listRadios();

	@GET("search")
	Call<List<SearchResult>> search(@Query("title") String searchTerm);

	@GET("programme")
	Call<List<Programme>> getProgramme(@Query("id") String programmeId);
}
