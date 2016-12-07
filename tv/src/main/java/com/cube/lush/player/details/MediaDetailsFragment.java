package com.cube.lush.player.details;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import com.cube.lush.player.playback.PlaybackActivity;
import com.cube.lush.player.playback.PlaybackMethod;
import com.cube.lush.player.model.MediaContent;
import com.cube.lush.player.model.RadioContent;

/**
 * Displays details for a specific {@link MediaContent}, with a thumbnail for the content being loaded and revealed in the right-hand pane.
 *
 * @author Jamie Cruwys
 * @project lush-player-android-client
 */
public class MediaDetailsFragment extends BaseMediaDetailsFragment
{
	@Override public void populateContentView(@NonNull MediaContent item)
	{
		super.populateContentView(item);

		liveIndicator.setVisibility(View.GONE);
		startEndTime.setVisibility(View.GONE);
		timeRemaining.setVisibility(View.GONE);

		playButton.setText("Play");
		startEndTime.setText(item.getDate().toString());
	}

	@Override public void playButtonClicked(View view)
	{
		if (getActivity() == null)
		{
			return;
		}

		Context context = getActivity();
		String id = null;

		if (mediaContent == null)
		{
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
				break;
			}
		}
	}
}
