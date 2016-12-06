package com.cube.lush.player;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.brightcove.player.analytics.Analytics;
import com.brightcove.player.edge.Catalog;
import com.brightcove.player.edge.PlaylistListener;
import com.brightcove.player.edge.VideoListener;
import com.brightcove.player.event.EventEmitter;
import com.brightcove.player.media.DeliveryType;
import com.brightcove.player.model.Playlist;
import com.brightcove.player.model.Video;
import com.brightcove.player.view.BaseVideoView;
import com.brightcove.player.view.BrightcovePlayerFragment;

/**
 * Uses the Brightcove SDK player to playback Lush-related content, including playlists, specific videos, or remote files.
 *
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

		Intent intent = getActivity().getIntent();

		if (intent != null)
		{
			PlaybackMethod playbackMethod = (PlaybackMethod)intent.getSerializableExtra(PlaybackActivity.ARGUMENT_PLAYBACK_METHOD);
			String playbackMethodValue = intent.getStringExtra(PlaybackActivity.ARGUMENT_PLAYBACK_METHOD_VALUE);

			if (playbackMethod != null && !TextUtils.isEmpty(playbackMethodValue))
			{
				if (playbackMethod == PlaybackMethod.PLAYLIST)
				{
					queuePlaylist(playbackMethodValue);
				}
				else if (playbackMethod == PlaybackMethod.VIDEO)
				{
					queueVideo(playbackMethodValue);
				}
				else if (playbackMethod == PlaybackMethod.FILE_URL)
				{
					playFile(playbackMethodValue);
				}
			}
		}
	}

	/**
	 * Queue a particular Brightcove playlist in the Brightcove player.
	 *
	 * @param playlistId
	 */
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

			@Override
			public void onError(String error)
			{
				super.onError(error);
			}
		});
	}

	/**
	 * Queue a particular Brightcove video in the Brightcove player.
	 *
	 * @param videoId
	 */
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

			@Override public void onError(String error)
			{
				super.onError(error);

				Activity activity = getActivity();

				if (activity != null)
				{
					Toast.makeText(activity, "Error: " + error, Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	public void playFile(@NonNull String fileUrl)
	{
		brightcoveVideoView.stopPlayback();

		//if (fileUrl.endsWith(".mp4"))
		{
			brightcoveVideoView.add(Video.createVideo(fileUrl, DeliveryType.MP4));
			brightcoveVideoView.start();
		}
	}
}
