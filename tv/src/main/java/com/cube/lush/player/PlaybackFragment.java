package com.cube.lush.player;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brightcove.player.analytics.Analytics;
import com.brightcove.player.edge.Catalog;
import com.brightcove.player.edge.PlaylistListener;
import com.brightcove.player.edge.VideoListener;
import com.brightcove.player.event.EventEmitter;
import com.brightcove.player.mediacontroller.BrightcoveMediaController;
import com.brightcove.player.model.Playlist;
import com.brightcove.player.model.Video;
import com.brightcove.player.view.BaseVideoView;
import com.brightcove.player.view.BrightcovePlayerFragment;

/**
 * Created by tim on 24/11/2016.
 */
public class PlaybackFragment extends BrightcovePlayerFragment
{
	private Catalog catalog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_playback, container, false);
		brightcoveVideoView = (BaseVideoView) view.findViewById(R.id.brightcove_video_view);
		brightcoveVideoView.setMediaController((BrightcoveMediaController) null);

		super.onCreateView(inflater, container, savedInstanceState);

		String accountId = getResources().getString(R.string.brightcove_account_id);
		Analytics analytics = brightcoveVideoView.getAnalytics();
		analytics.setAccount(accountId);

		// Get the event emitter from the SDK and create a catalog request to fetch a video from the
		// Brightcove Edge service, given a video id, an account id and a policy key.
		EventEmitter eventEmitter = brightcoveVideoView.getEventEmitter();
		catalog = new Catalog(eventEmitter, accountId, getResources().getString(R.string.brightcove_policy_key));

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		// TODO: Try and get the video or playlist ID from the activity intent
	}

	public void queuePlaylist(String playlistId)
	{
		brightcoveVideoView.stopPlayback();

		if (TextUtils.isEmpty(playlistId))
		{
			return;
		}

		catalog.findPlaylistByID(playlistId, new PlaylistListener()
		{
			@Override
			public void onPlaylist(Playlist playlist)
			{
				brightcoveVideoView.addAll(playlist.getVideos());
				brightcoveVideoView.start();
			}
		});
	}
	public void queueVideo(String videoId)
	{
		brightcoveVideoView.stopPlayback();

		if (TextUtils.isEmpty(videoId))
		{
			return;
		}

		catalog.findVideoByID(videoId, new VideoListener()
		{
			// Add the video found to the queue with add().
			// Start playback of the video with start().
			@Override
			public void onVideo(Video video)
			{
				brightcoveVideoView.add(video);
				brightcoveVideoView.start();
			}
		});
	}
}
