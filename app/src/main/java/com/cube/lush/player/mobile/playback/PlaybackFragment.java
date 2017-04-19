package com.cube.lush.player.mobile.playback;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brightcove.player.analytics.Analytics;
import com.brightcove.player.edge.PlaylistListener;
import com.brightcove.player.edge.VideoListener;
import com.brightcove.player.media.DeliveryType;
import com.brightcove.player.mediacontroller.BrightcoveMediaController;
import com.brightcove.player.model.Playlist;
import com.brightcove.player.model.Video;
import com.brightcove.player.view.BaseVideoView;
import com.brightcove.player.view.BrightcovePlayerFragment;
import com.cube.lush.player.R;
import com.cube.lush.player.api.model.ContentType;
import com.cube.lush.player.api.model.MediaContent;
import com.cube.lush.player.api.model.Programme;
import com.cube.lush.player.api.model.RadioContent;
import com.cube.lush.player.content.handler.ResponseHandler;
import com.cube.lush.player.content.manager.MediaManager;
import com.cube.lush.player.content.model.VideoInfo;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import uk.co.jamiecruwys.StatefulActivity;
import uk.co.jamiecruwys.ViewState;
import uk.co.jamiecruwys.contracts.ViewStateChange;

/**
 * Created by Jamie Cruwys.
 */
public class PlaybackFragment extends BrightcovePlayerFragment implements ViewStateChange
{
	private MediaContent mediaContent;

	public PlaybackFragment()
	{
		// Required empty public constructor
	}

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.playback_fragment, container, false);
		brightcoveVideoView = (BaseVideoView) view.findViewById(R.id.brightcove_video_view);
		brightcoveVideoView.setMediaController(new BrightcoveMediaController(brightcoveVideoView, R.layout.one_line_brightcove_media_controller));
		ButterKnife.bind(this, view);

		super.onCreateView(inflater, container, savedInstanceState);

		Analytics analytics = brightcoveVideoView.getAnalytics();
		analytics.setAccount(com.cube.lush.player.content.BuildConfig.BRIGHTCOVE_ACCOUNT_ID);

		return view;
	}

	@Override public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		if (getActivity() == null)
		{
			return;
		}

		if (getActivity().getIntent() == null)
		{
			setViewState(ViewState.ERROR);
			return;
		}

		mediaContent = (MediaContent)getActivity().getIntent().getSerializableExtra(PlaybackActivity.EXTRA_MEDIA_CONTENT);
		playMediaContent(mediaContent);
	}

	public void playMediaContent(@NonNull MediaContent mediaContent)
	{
		if (brightcoveVideoView.isPlaying())
		{
			brightcoveVideoView.stopPlayback();
		}

		// Load image
		Picasso.with(brightcoveVideoView.getContext())
			.load(mediaContent.getThumbnail())
			.memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
			.into(brightcoveVideoView.getStillView());

		ContentType contentType = mediaContent.getType();
		String id = mediaContent.getId();

		if (contentType == ContentType.TV)
		{
			playVideo(id);
		}
		else
		{
			if (mediaContent instanceof RadioContent)
			{
				String fileUrl = ((RadioContent)mediaContent).getFile();
				playAudio(fileUrl);
			}
			else if (mediaContent instanceof Programme)
			{
				String fileUrl = ((Programme)mediaContent).getUrl();
				playAudio(fileUrl);
			}
			else
			{
				// Go and get the radio content using get programme endpoint
				fetchRadioDetails(brightcoveVideoView.getContext(), mediaContent.getId());
			}
		}

		// TODO: Get live playback working
	}

	protected void fetchRadioDetails(@NonNull final Context context, final String mediaId)
	{
		MediaManager.getInstance().getProgramme(mediaId, new ResponseHandler<Programme>()
		{
			@Override public void onSuccess(@NonNull List<Programme> items)
			{
				if (getActivity() == null)
				{
					return;
				}

				if (items.isEmpty() || items.get(0) == null)
				{
					setViewState(ViewState.ERROR);
					return;
				}

				playMediaContent(items.get(0));
			}

			@Override public void onFailure(@Nullable Throwable t)
			{
				if (getActivity() == null)
				{
					return;
				}

				setViewState(ViewState.ERROR);
			}
		});
	}

	private void playVideo(@NonNull String videoId)
	{
		MediaManager.getInstance().getCatalog().findVideoByID(videoId, new VideoListener()
		{
			// Add the video found to the queue with add().
			// Start playback of the video with start().
			@Override
			public void onVideo(Video video)
			{
				// This method is designed to be called from async methods so make sure we've not lost context since then
				if (getActivity() == null)
				{
					return;
				}

				setViewState(ViewState.LOADED);

				brightcoveVideoView.add(video);
				brightcoveVideoView.start();
				brightcoveVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
				{
					@Override
					public void onCompletion(MediaPlayer mediaPlayer)
					{
						Activity activity = getActivity();

						if (activity != null)
						{
							activity.finish();
						}
					}
				});
			}

			@Override public void onError(String error)
			{
				super.onError(error);

				if (getActivity() == null)
				{
					return;
				}

				setViewState(ViewState.ERROR);
			}
		});
	}

	private void playAudio(@NonNull String fileUrl)
	{
		if (getActivity() == null)
		{
			return;
		}

		setViewState(ViewState.LOADED);

		brightcoveVideoView.add(Video.createVideo(fileUrl, DeliveryType.MP4));
		brightcoveVideoView.start();
		brightcoveVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
		{
			@Override
			public void onCompletion(MediaPlayer mediaPlayer)
			{
				Activity activity = getActivity();

				if (activity != null)
				{
					activity.finish();
				}
			}
		});
	}

	private void playPlaylist(@NonNull String playlistId)
	{
		MediaManager.getInstance().getCatalog().findPlaylistByID(playlistId, new PlaylistListener()
		{
			@Override
			public void onPlaylist(Playlist playlist)
			{
				// This method is designed to be called from async methods so make sure we've not lost context since then
				if (getActivity() == null)
				{
					return;
				}

				VideoInfo videoInfo = MediaManager.getInstance().findCurrentLiveVideo(playlist);

				if (videoInfo != null)
				{
					setViewState(ViewState.LOADED);

					brightcoveVideoView.add(videoInfo.getVideo());
					brightcoveVideoView.seekTo((int) (System.currentTimeMillis() - videoInfo.getStartTimeUtc()));
					brightcoveVideoView.start();
					brightcoveVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
					{
						@Override
						public void onCompletion(MediaPlayer mediaPlayer)
						{
							playLive();
						}
					});
				}
				else
				{
					setViewState(ViewState.ERROR);
				}
			}

			@Override
			public void onError(String error)
			{
				super.onError(error);

				if (getActivity() == null)
				{
					return;
				}

				setViewState(ViewState.ERROR);
			}
		});
	}

	private void playLive()
	{
		MediaManager.getInstance().getLiveContent(new ResponseHandler<MediaContent>()
		{
			@Override
			public void onSuccess(@NonNull List<MediaContent> items)
			{
				// This method is designed to be called from async methods so make sure we've not lost context since then
				if (getActivity() == null)
				{
					return;
				}

				if (!items.isEmpty() && !TextUtils.isEmpty(items.get(0).getId()))
				{
					setViewState(ViewState.LOADED);
					playPlaylist(items.get(0).getId());
				}
				else
				{
					setViewState(ViewState.ERROR);
				}
			}

			@Override
			public void onFailure(@Nullable Throwable t)
			{
				if (getActivity() == null)
				{
					return;
				}

				setViewState(ViewState.ERROR);
			}
		});
	}

	@Override public void setViewState(@NonNull ViewState viewState)
	{
		((StatefulActivity)getActivity()).setViewState(viewState);
	}

	@NonNull @Override public ViewState getViewState()
	{
		return ((StatefulActivity)getActivity()).getViewState();
	}
}