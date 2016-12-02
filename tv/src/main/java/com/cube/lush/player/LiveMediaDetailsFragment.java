package com.cube.lush.player;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.animation.AnimationUtils;

import com.cube.lush.player.model.MediaContent;

/**
 * @author Jamie Cruwys
 * @project lush-player-android-client
 */
public class LiveMediaDetailsFragment extends BaseMediaDetailsFragment
{
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		MediaContent dummyLiveItem = new MediaContent();
		dummyLiveItem.setTitle("Live");
		dummyLiveItem.setDescription("Watch live Lush TV");
		populateContentView(dummyLiveItem);
	}

	@Override public void populateContentView(@NonNull MediaContent item)
	{
		super.populateContentView(item);

		// TODO:
		//		startEndTime.setText("");
		//		timeRemaining.setText("");

		Drawable circleDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.circle);
		liveIndicator.setImageDrawable(circleDrawable);

		int circleColour = ContextCompat.getColor(getActivity(), R.color.material_red);
		liveIndicator.getDrawable().setColorFilter(circleColour, PorterDuff.Mode.MULTIPLY);

		liveIndicator.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.pulse));

		revealContentView();
	}

	@Override public void revealContentView()
	{
		super.revealContentView();

		populateHiddenView(mediaContent);
	}

	@Override public void populateHiddenView(@NonNull MediaContent item)
	{
		revealHiddenView();
	}

	@Override public void revealHiddenView()
	{
		super.revealHiddenView();
	}
}
