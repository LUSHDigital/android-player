package com.cube.lush.player.tv.details;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.brightcove.player.edge.PlaylistListener;
import com.brightcove.player.model.Playlist;
import com.brightcove.player.model.Video;
import com.cube.lush.player.R;
import com.lush.player.api.model.ContentType;
import com.lush.player.api.model.LivePlaylist;
import com.lush.player.api.model.Programme;
import com.cube.lush.player.content.brightcove.BrightcoveCatalog;
import com.cube.lush.player.content.brightcove.BrightcoveUtils;
import com.cube.lush.player.content.handler.ResponseHandler;
import com.cube.lush.player.content.model.VideoInfo;
import com.cube.lush.player.content.repository.LivePlaylistRepository;
import com.cube.lush.player.tv.adapter.BasicMainFragmentAdapter;
import com.cube.lush.player.tv.playback.PlaybackActivity;
import com.cube.lush.player.tv.playback.PlaybackMethod;

import java.util.List;

import static android.text.format.DateUtils.FORMAT_SHOW_TIME;
import static android.text.format.DateUtils.FORMAT_UTC;

/**
 * Displays details and a preview of the current live Lush playlist.
 *
 * @author Jamie Cruwys
 */
public class LiveDetailsFragment extends BaseDetailsFragment implements BrowseFragment.MainFragmentAdapterProvider
{
	/**
	 * How often the live content should be refreshed in order to update current show / time remaining etc.
	 */
	private static final long REFRESH_INTERVAL_MS = 1000 * 60;

	private BrowseFragment.MainFragmentAdapter<LiveDetailsFragment> mainFragmentAdapter;
	/**
	 * Handler to auto-refresh the live content with
	 */
	private Handler handler = new Handler();
	/**
	 * Runnable to be called periodically in order to refresh the live content
	 */
	private Runnable scheduledFetch = new Runnable()
	{
		@Override
		public void run()
		{
			if (getActivity() == null)
			{
				return;
			}

			fetchPlaylist();
			handler.postDelayed(scheduledFetch, REFRESH_INTERVAL_MS);
		}
	};

	@Override
	public void onStart()
	{
		super.onStart();
		// TODO: Add back live check once the API works again.
		// handler.post(scheduledFetch);

		OffAirFragment.show(getChildFragmentManager(), contentContainer);
	}

	@Override
	public void onStop()
	{
		// handler.removeCallbacks(scheduledFetch);
		super.onStop();
	}

	private void fetchPlaylist()
	{
		LivePlaylistRepository.getInstance(getActivity()).getItems(new ResponseHandler<LivePlaylist>()
		{
			@Override public void onSuccess(@NonNull List<LivePlaylist> items)
			{
				// This method is designed to be called from async methods so make sure we've not lost context since then
				if (getActivity() == null)
				{
					return;
				}

				if (!items.isEmpty())
				{
					setPlaylistId(items.get(0).getId());
				}
				else
				{
					OffAirFragment.show(getChildFragmentManager(), contentContainer);
				}
			}

			@Override public void onFailure(@Nullable Throwable t)
			{
				populateError();
			}
		});
	}

	private void populateError()
	{
		populateError(new Runnable()
		{
			@Override
			public void run()
			{
				fetchPlaylist();
			}
		});
	}

	private void setPlaylistId(final String playlistId)
	{
		BrightcoveCatalog.INSTANCE.getCatalog().findPlaylistByID(playlistId, new PlaylistListener()
		{
			@Override
			public void onPlaylist(Playlist playlist)
			{
				// This method is designed to be called from async methods so make sure we've not lost context since then
				if (getActivity() == null)
				{
					return;
				}

				VideoInfo liveVideoInfo = BrightcoveUtils.findCurrentLiveVideo(playlist);

				if (liveVideoInfo != null)
				{
					setLiveVideoInfo(playlistId, liveVideoInfo);
				}
				else
				{
					OffAirFragment.show(getChildFragmentManager(), contentContainer);
				}
			}

			@Override
			public void onError(String error)
			{
				super.onError(error);
				populateError();
			}
		});
	}

	private void setLiveVideoInfo(@NonNull String playlistId, @NonNull VideoInfo videoInfo)
	{
		OffAirFragment.hide(getChildFragmentManager());

		long nowUtc = System.currentTimeMillis();

		// We construct a dummy MediaContent item to represent the live content for the base class to use
		Video video = videoInfo.getVideo();
		Programme liveProgramme = new Programme();
		liveProgramme.setId(playlistId);
		liveProgramme.setType(ContentType.TV);

		String name = BrightcoveUtils.getVideoName(video);
		if (TextUtils.isEmpty(name))
		{
			liveProgramme.setTitle(getString(R.string.live_no_title));
		}
		else
		{
			liveProgramme.setTitle(String.format(getString(R.string.live_title), name));
		}

		long timeRemainingMillis = videoInfo.getEndTimeUtc() - nowUtc;
		long timeRemainingMins = timeRemainingMillis / 1000 / 60 + 1;
		timeRemaining.setText(getString(R.string.minutes_remaining, timeRemainingMins));
		startEndTime.setText(DateUtils.formatDateRange(startEndTime.getContext(),
		                                               videoInfo.getStartTimeUtc(),
		                                               videoInfo.getEndTimeUtc(),
		                                               FORMAT_SHOW_TIME | FORMAT_UTC));

		liveProgramme.setThumbnail(BrightcoveUtils.getVideoThumbnail(video));

		populateContentView(liveProgramme);
	}

	@Override
	public void populateContentView(@NonNull Programme item)
	{
		super.populateContentView(item);

		// This method is designed to be called from async methods so make sure we've not lost context since then
		if (getActivity() == null)
		{
			return;
		}

		Drawable circleDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.circle);
		liveIndicator.setImageDrawable(circleDrawable);

		int circleColour = ContextCompat.getColor(getActivity(), R.color.material_red);
		liveIndicator.getDrawable().setColorFilter(circleColour, PorterDuff.Mode.MULTIPLY);

		liveIndicator.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.pulse));
	}

	@Override
	public void playButtonClicked(View view)
	{
		Context context = getActivity();

		// This method is designed to be called from async methods so make sure we've not lost context since then
		if (context == null)
		{
			return;
		}

		if (programme != null)
		{
			Intent intent = PlaybackActivity.getIntent(context, PlaybackMethod.PLAYLIST, programme.getId(), programme.getThumbnail());
			getActivity().startActivity(intent);
		}
	}

	@Override
	public BrowseFragment.MainFragmentAdapter<LiveDetailsFragment> getMainFragmentAdapter()
	{
		if (mainFragmentAdapter == null)
		{
			mainFragmentAdapter = new BasicMainFragmentAdapter<>(this);
		}
		return mainFragmentAdapter;
	}
}
