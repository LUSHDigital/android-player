package com.cube.lush.player;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.View;

import com.cube.lush.player.model.MediaContent;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

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

		watchButton.setText("Play");
		startEndTime.setText(item.getDate().toString());

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
