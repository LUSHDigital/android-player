package com.cube.lush.player.tv.details;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Toast;

import com.cube.lush.player.R;
import com.lush.player.api.model.Programme;
import com.cube.lush.player.tv.playback.PlaybackActivity;
import com.cube.lush.player.tv.playback.PlaybackMethod;

/**
 * Displays details for a specific {@link Programme}, with a thumbnail for the content being loaded and revealed in the right-hand pane.
 *
 * @author Jamie Cruwys
 */
public class ProgrammeDetailsFragment extends BaseDetailsFragment
{
	@Override public void populateContentView(@NonNull final Programme item)
	{
		super.populateContentView(item);
		liveIndicator.setVisibility(View.GONE);
		timeRemaining.setVisibility(View.GONE);

		playButton.setText(R.string.play);

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

		if (programme == null || programme.getType() == null)
		{
			Toast.makeText(context, R.string.media_unplayable, Toast.LENGTH_LONG).show();
			return;
		}

		switch (programme.getType())
		{
			case TV:
			{
				Intent intent = PlaybackActivity.getIntent(context, PlaybackMethod.VIDEO, programme.getId(), programme.getThumbnail());
				getActivity().startActivity(intent);
				break;
			}
			case RADIO:
			{
				Intent intent = PlaybackActivity.getIntent(context, PlaybackMethod.FILE_URL, programme.getFile(), programme.getThumbnail());
				getActivity().startActivity(intent);
			}
		}
	}
}
