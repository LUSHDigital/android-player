package com.cube.lush.player.details;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Toast;

import com.cube.lush.player.SpinnerFragment;
import com.cube.lush.player.content.handler.ResponseHandler;
import com.cube.lush.player.content.manager.MediaManager;
import com.cube.lush.player.api.model.MediaContent;
import com.cube.lush.player.api.model.Programme;
import com.cube.lush.player.api.model.RadioContent;
import com.cube.lush.player.api.model.SearchResult;
import com.cube.lush.player.playback.PlaybackActivity;
import com.cube.lush.player.playback.PlaybackMethod;

import java.util.List;

/**
 * Displays details for a specific {@link MediaContent}, with a thumbnail for the content being loaded and revealed in the right-hand pane.
 *
 * @author Jamie Cruwys
 * @project lush-player-android-client
 */
public class MediaDetailsFragment extends BaseMediaDetailsFragment
{
	@Override public void populateContentView(@NonNull final MediaContent item)
	{
		super.populateContentView(item);
		liveIndicator.setVisibility(View.GONE);
		timeRemaining.setVisibility(View.GONE);

		playButton.setText("Play");

		if (item.getDate() != null)
		{
			startEndTime.setText(DateUtils.formatDateTime(getActivity(), item.getDate().getTime(), 0));
		}

		// Search results only have quite a basic amount of data, so fetch their details to get the full payload and then repopulate the content
		if (item instanceof SearchResult)
		{
			MediaManager.getInstance().getProgramme(mediaContent.getId(), new ResponseHandler<Programme>()
			{
				@Override
				public void onSuccess(@NonNull List<Programme> items)
				{
					if (items != null && !items.isEmpty())
					{
						items.get(0).setType(item.getType()); // Programme endpoint doesn't send back a type, so use the one from the original object...
						populateContentView(items.get(0));
					}
				}

				@Override
				public void onFailure(@Nullable Throwable t)
				{

				}
			});
		}
	}

	@Override public void playButtonClicked(View view)
	{
		Context context = getActivity();

		if (context == null)
		{
			return;
		}

		if (mediaContent == null || mediaContent.getType() == null)
		{
			Toast.makeText(context, "Could not play media", Toast.LENGTH_LONG).show();
			return;
		}

		switch (mediaContent.getType())
		{
			case TV:
			{
				Intent intent = PlaybackActivity.getIntent(context, PlaybackMethod.VIDEO, mediaContent.getId(), mediaContent.getThumbnail());
				getActivity().startActivity(intent);
				break;
			}
			case RADIO:
			{
				if (mediaContent instanceof RadioContent)
				{
					Intent intent = PlaybackActivity.getIntent(context, PlaybackMethod.FILE_URL, ((RadioContent)mediaContent).getFile(), mediaContent.getThumbnail());
					getActivity().startActivity(intent);
				}
				else if (mediaContent instanceof Programme)
				{
					Intent intent = PlaybackActivity.getIntent(context, PlaybackMethod.FILE_URL, ((Programme)mediaContent).getUrl(), mediaContent.getThumbnail());
					getActivity().startActivity(intent);
				}
				else
				{
					// Go and get the radio content using get programme endpoint
					SpinnerFragment.show(getChildFragmentManager(), contentContainer);
					fetchRadioDetails(context, mediaContent.getId());
				}
				break;
			}
		}
	}

	protected void fetchRadioDetails(@NonNull final Context context, final String mediaId)
	{
		MediaManager.getInstance().getProgramme(mediaId, new ResponseHandler<Programme>()
		{
			@Override public void onSuccess(@NonNull List<Programme> items)
			{
				SpinnerFragment.hide(getChildFragmentManager());

				if (!items.isEmpty())
				{
					Programme programme = items.get(0);

					if (programme == null)
					{
						return;
					}

					if (context != null)
					{
						// Play the content now we have the url
						Intent intent = PlaybackActivity.getIntent(context, PlaybackMethod.FILE_URL, programme.getUrl(), programme.getThumbnail());
						context.startActivity(intent);
					}
				}
			}

			@Override public void onFailure(@Nullable Throwable t)
			{
				SpinnerFragment.hide(getChildFragmentManager());

				if (context != null)
				{
					Toast.makeText(context, "Unable to play media, please try again", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}
