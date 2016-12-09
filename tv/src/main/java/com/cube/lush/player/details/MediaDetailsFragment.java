package com.cube.lush.player.details;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Toast;

import com.cube.lush.player.model.MediaContent;
import com.cube.lush.player.model.RadioContent;
import com.cube.lush.player.playback.PlaybackActivity;
import com.cube.lush.player.playback.PlaybackMethod;

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
		timeRemaining.setVisibility(View.GONE);

		playButton.setText("Play");

		if (item.getDate() != null)
		{
			startEndTime.setText(DateUtils.formatDateTime(getActivity(), item.getDate().getTime(), 0));
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
				break;
			}
		}
	}
}
