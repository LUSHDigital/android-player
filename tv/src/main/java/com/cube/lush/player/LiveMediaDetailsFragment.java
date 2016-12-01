package com.cube.lush.player;

import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.cube.lush.player.model.MediaContent;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * @author Jamie Cruwys
 * @project lush-player-android-client
 */
public class LiveMediaDetailsFragment extends BaseMediaDetailsFragment
{
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
		ImageLoader.getInstance().loadImage(item.getThumbnail(), new ImageLoadingListener()
		{
			@Override
			public void onLoadingStarted(String imageUri, View view)
			{

			}

			@Override
			public void onLoadingFailed(String imageUri, View view, FailReason failReason)
			{

			}

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage)
			{
				if (backgroundImage != null)
				{
					backgroundImage.setImageBitmap(loadedImage);
					revealHiddenView();
				}
			}

			@Override
			public void onLoadingCancelled(String imageUri, View view)
			{

			}
		});
	}

	@Override public void revealHiddenView()
	{
		super.revealHiddenView();
	}
}
