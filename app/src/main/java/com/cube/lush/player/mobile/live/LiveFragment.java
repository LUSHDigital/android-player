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
import android.widget.TextView;

import com.brightcove.player.edge.PlaylistListener;
import com.brightcove.player.model.Playlist;
import com.brightcove.player.model.Video;
import com.cube.lush.player.R;
import com.cube.lush.player.content.brightcove.BrightcoveCatalog;
import com.cube.lush.player.content.brightcove.BrightcoveUtils;
import com.cube.lush.player.content.handler.ResponseHandler;
import com.cube.lush.player.content.model.VideoInfo;
import com.cube.lush.player.content.repository.LivePlaylistRepository;
import com.cube.lush.player.mobile.LushTab;
import com.cube.lush.player.mobile.MainActivity;
import com.cube.lush.player.mobile.content.TagContentFragment;
import com.cube.lush.player.mobile.playback.LushPlaybackActivity;
import com.cube.lush.player.mobile.view.TagClickListener;
import com.cube.lush.player.mobile.view.TagSectionView;
import com.lush.player.api.model.ContentType;
import com.lush.player.api.model.LivePlaylist;
import com.lush.player.api.model.Programme;
import com.lush.player.api.model.Tag;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.jamiecruwys.StatefulFragment;
import uk.co.jamiecruwys.ViewState;
import uk.co.jamiecruwys.contracts.ListingData;

import static android.text.format.DateUtils.FORMAT_SHOW_TIME;
import static android.text.format.DateUtils.FORMAT_UTC;

/**
 * Live Fragment
 *
 * @author Jamie Cruwys
 */
public class LiveFragment extends StatefulFragment<Playlist>
{
	@BindView(R.id.thumbnail) ImageView thumbnail;
	@BindView(R.id.meta) TextView meta;
	@BindView(R.id.title) TextView title;
	@BindView(R.id.description) TextView description;
	@BindView(R.id.time_remaining) TextView timeRemaining;
	@BindView(R.id.tag_section) TagSectionView tagSection;
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

		if (view != null)
		{
			ButterKnife.bind(this, view);
		}

		return view;
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

	@Override public ViewState provideInitialViewState()
	{
		return ViewState.EMPTY;
	}

	@Override protected boolean shouldReloadOnResume()
	{
		return true;
	}

	@Override protected void getListData(@NonNull final ListingData callback)
	{
		LivePlaylistRepository.getInstance(getContext()).getItems(new ResponseHandler<LivePlaylist>()
		{
			@Override public void onSuccess(@NonNull List<LivePlaylist> items)
			{
				if (getActivity() == null)
				{
					if (callback != null)
					{
						callback.onListingDataError(null);
					}
					return;
				}

				if (items.isEmpty())
				{
					if (callback != null)
					{
						callback.onListingDataRetrieved(Collections.EMPTY_LIST);
					}
					return;
				}

				final String playlistId = items.get(0).getId();

				// Get the playlist that is live
				BrightcoveCatalog.INSTANCE.getCatalog().findPlaylistByID(playlistId, new PlaylistListener()
				{
					@Override
					public void onPlaylist(Playlist playlist)
					{
						if (getActivity() == null || playlist == null)
						{
							if (callback != null)
							{
								callback.onListingDataError(null);
							}
							return;
						}

						playlist.getProperties().put("id", playlistId);

						ArrayList<Playlist> playlists = new ArrayList<>();
						playlists.add(playlist);

						if (callback != null)
						{
							callback.onListingDataRetrieved(playlists);
						}
					}

					@Override
					public void onError(String error)
					{
						if (callback != null)
						{
							callback.onListingDataError(null);
						}
					}
				});
			}

			@Override public void onFailure(@Nullable Throwable t)
			{
				if (callback != null)
				{
					callback.onListingDataError(null);
				}
			}
		});
	}

	@Override public void onListingDataRetrieved(@NonNull List<Playlist> items)
	{
		// Cover us from null pointers where the activity/fragment is detached
		if (!isAdded() || getActivity() == null)
		{
			return;
		}

		super.onListingDataRetrieved(items);

		if (getViewState() == ViewState.LOADED)
		{
			// Will only be one playlist element
			Playlist playlist = items.get(0);
			String playlistId = playlist.getStringProperty("id");

			VideoInfo liveVideoInfo = BrightcoveUtils.findCurrentLiveVideo(playlist);

			if (liveVideoInfo == null)
			{
				setViewState(ViewState.EMPTY);
				return;
			}

			setLiveVideoInfo(playlistId, liveVideoInfo);
		}
	}

	private void setLiveVideoInfo(@NonNull final String playlistId, @NonNull final VideoInfo videoInfo)
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
		long timeRemainingMins = (timeRemainingMillis / 1000 / 60) + 1;

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
				play(playlistId, videoInfo);
			}
		});

		tagSection.setTagClickListener(new TagClickListener()
		{
			@Override
			public void onTagClick(@NonNull Tag tag)
			{
				((MainActivity)getActivity()).showFragment(TagContentFragment.newInstance(tag));
			}
		});

		List<Tag> tags = BrightcoveUtils.getVideoTags(brightcoveVideo);
		tagSection.setTags(tags);
	}

	private void play(@NonNull String playlistId, @NonNull final VideoInfo videoInfo)
	{
		Programme programme = new Programme();
		programme.setId(videoInfo.getVideo().getId());
		programme.setType(ContentType.TV);

		Intent playbackIntent = LushPlaybackActivity.getIntent(getContext(), programme, 0);
		getActivity().startActivity(playbackIntent);
	}

	@OnClick(R.id.show_channels) void onShowChannelsClicked()
	{
		((MainActivity)getActivity()).selectTab(LushTab.CHANNELS);
	}

	@OnClick(R.id.share) void onShareClicked()
	{
		// TODO: Once we can get the web url
	}
}