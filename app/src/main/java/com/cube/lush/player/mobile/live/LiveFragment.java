package com.cube.lush.player.mobile.live;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.brightcove.player.edge.PlaylistListener;
import com.brightcove.player.model.Playlist;
import com.brightcove.player.model.Video;
import com.cube.lush.player.R;
import com.cube.lush.player.api.model.ContentType;
import com.cube.lush.player.api.model.MediaContent;
import com.cube.lush.player.content.handler.ResponseHandler;
import com.cube.lush.player.content.manager.MediaManager;
import com.cube.lush.player.content.model.VideoInfo;
import com.cube.lush.player.content.util.BrightcoveUtils;
import com.cube.lush.player.mobile.LushTab;
import com.cube.lush.player.mobile.MainActivity;
import com.cube.lush.player.mobile.playback.PlaybackActivity;
import com.squareup.picasso.Picasso;

import org.apmem.tools.layouts.FlowLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.jamiecruwys.StatefulFragment;
import uk.co.jamiecruwys.ViewState;

import static android.text.format.DateUtils.FORMAT_SHOW_TIME;
import static android.text.format.DateUtils.FORMAT_UTC;

/**
 * Created by Jamie Cruwys.
 */
public class LiveFragment extends StatefulFragment
{
	@BindView(R.id.thumbnail) ImageView thumbnail;
	@BindView(R.id.meta) TextView meta;
	@BindView(R.id.title) TextView title;
	@BindView(R.id.description) TextView description;
	@BindView(R.id.time_remaining) TextView timeRemaining;
	@BindView(R.id.tag_section) LinearLayout tagSection;
	@BindView(R.id.tag_list) FlowLayout tagList;
	@BindView(R.id.play_button) ImageButton playButton;

	public LiveFragment()
	{
		// Required empty public constructor
	}

	public static LiveFragment newInstance()
	{
		LiveFragment fragment = new LiveFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = super.onCreateView(inflater, container, savedInstanceState);
		ButterKnife.bind(this, view);
		return view;
	}

	@Override public void onResume()
	{
		super.onResume();

		// Get the live content
		MediaManager.getInstance().getLiveContent(new ResponseHandler<MediaContent>()
		{
			@Override
			public void onSuccess(@NonNull List<MediaContent> items)
			{
				if (getActivity() == null)
				{
					setViewState(ViewState.ERROR);
					return;
				}

				if (items.isEmpty())
				{
					setViewState(ViewState.EMPTY);
					return;
				}

				final String playlistId = items.get(0).getId();

				// Get the playlist that is live
				MediaManager.getInstance().getCatalog().findPlaylistByID(playlistId, new PlaylistListener()
				{
					@Override
					public void onPlaylist(Playlist playlist)
					{
						if (getActivity() == null)
						{
							setViewState(ViewState.ERROR);
							return;
						}

						if (playlist == null)
						{
							setViewState(ViewState.EMPTY);
							return;
						}

						VideoInfo liveVideoInfo = MediaManager.getInstance().findCurrentLiveVideo(playlist);

						if (liveVideoInfo == null)
						{
							setViewState(ViewState.EMPTY);
							return;
						}

						setLiveVideoInfo(playlistId, liveVideoInfo);
					}

					@Override
					public void onError(String error)
					{
						super.onError(error);
						setViewState(ViewState.ERROR);
					}
				});
			}

			@Override
			public void onFailure(@Nullable Throwable t)
			{
				setViewState(ViewState.ERROR);
			}
		});
	}

	@Override public ViewState provideInitialViewState()
	{
		return ViewState.LOADING;
	}

	@Override public int provideLoadingLayout()
	{
		return R.layout.live_loading;
	}

	@Override public int provideEmptyLayout()
	{
		return R.layout.live_empty;
	}

	@Override public int provideLoadedLayout()
	{
		return R.layout.live_loaded;
	}

	@Override public int provideErrorLayout()
	{
		return R.layout.live_error;
	}

	@OnClick(R.id.show_channels) void onShowChannelsClicked()
	{
		((MainActivity)getActivity()).selectTab(LushTab.CHANNELS);
	}

	private void setLiveVideoInfo(@NonNull final String playlistId, @NonNull VideoInfo videoInfo)
	{
		Video brightcoveVideo = videoInfo.getVideo();

		if (brightcoveVideo == null)
		{
			setViewState(ViewState.EMPTY);
			return;
		}

		// Title
		String titleString = BrightcoveUtils.getVideoName(brightcoveVideo);
		title.setText(getString(R.string.live_title, titleString));

		// Thumbnail
		Picasso.with(getContext())
			.load(BrightcoveUtils.getVideoThumbnail(brightcoveVideo))
			.into(thumbnail);

		long nowUtc = System.currentTimeMillis();
		long timeRemainingMillis = videoInfo.getEndTimeUtc() - nowUtc;
		long timeRemainingMins = timeRemainingMillis / 1000 / 60 + 1;

		// Start and end times meta
		String startEndTimesString = DateUtils.formatDateRange(getContext(), videoInfo.getStartTimeUtc(), videoInfo.getEndTimeUtc(), FORMAT_SHOW_TIME | FORMAT_UTC);
		meta.setText(startEndTimesString);

		// Time remaining
		timeRemaining.setText(getString(R.string.minutes_remaining, timeRemainingMins));

		// Play button
		playButton.setOnClickListener(new View.OnClickListener()
		{
			@Override public void onClick(View v)
			{
				MediaContent mediaContent = new MediaContent();
				mediaContent.setId(playlistId);
				mediaContent.setType(ContentType.TV);

				Intent playbackIntent = PlaybackActivity.getIntent(getContext(), mediaContent);
				getActivity().startActivity(playbackIntent);
			}
		});

		// TODO: Description
		// TODO: Tags
	}

	@OnClick(R.id.share) void onShareClicked()
	{
		// TODO: Once we can get the web url
	}
}