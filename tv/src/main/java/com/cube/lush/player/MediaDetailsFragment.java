package com.cube.lush.player;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v17.leanback.app.BrandedFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cube.lush.player.handler.ResponseHandler;
import com.cube.lush.player.manager.MediaManager;
import com.cube.lush.player.model.MediaContent;
import com.cube.lush.player.model.Programme;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.cube.lush.player.MediaDetailsActivity.EXTRA_MEDIA;
import static com.cube.lush.player.MediaDetailsActivity.EXTRA_MEDIA_ID;

/**
 * Created by tim on 24/11/2016.
 */
public class MediaDetailsFragment extends BrandedFragment
{
	@BindView(R.id.progress) ProgressBar progressBar;
	@BindView(R.id.container) LinearLayout contentContainer;
	@BindView(R.id.live_indicator) ImageView liveIndicator;
	@BindView(R.id.title) TextView title;
	@BindView(R.id.start_end_time) TextView startEndTime;
	@BindView(R.id.description) TextView description;
	@BindView(R.id.time_remaining) TextView timeRemaining;

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.media_details_fragment, container, false);
		ButterKnife.bind(this, view);

		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		Activity activity = getActivity();

		if (activity == null)
		{
			return;
		}

		Intent intent = activity.getIntent();

		if (intent == null)
		{
			return;
		}

		Object item = intent.getSerializableExtra(EXTRA_MEDIA);

		if (item != null && item instanceof MediaContent)
		{
			loadMediaContent((MediaContent)item);
			return;
		}

		final String mediaId = intent.getStringExtra(EXTRA_MEDIA_ID);

		if (TextUtils.isEmpty(mediaId))
		{
			return;
		}

		MediaManager.getInstance().getProgramme(mediaId, new ResponseHandler<Programme>()
		{
			@Override public void onSuccess(@NonNull List<Programme> items)
			{
				if (!items.isEmpty())
				{
					loadMediaContent(items.get(0));
				}
			}

			@Override public void onFailure(@Nullable Throwable t)
			{
				// TODO: Show error message
			}
		});
	}

	private void loadMediaContent(@NonNull MediaContent mediaContent)
	{
		makeContentVisible(true);
//		loadImage(mediaContent);
	}

	/**
	 * Toggles content (and progress bar) visibility
	 * @param shouldBeVisible
	 */
	private void makeContentVisible(boolean shouldBeVisible)
	{
		if (shouldBeVisible)
		{
			contentContainer.setVisibility(View.VISIBLE);
			progressBar.setVisibility(View.GONE);
		}
		else
		{
			contentContainer.setVisibility(View.GONE);
			progressBar.setVisibility(View.VISIBLE);
		}
	}

//	private void loadImage(@NonNull MediaContent item)
//	{
//		ImageLoader.getInstance().loadImage(item.getThumbnail(), new ImageLoadingListener()
//		{
//			@Override
//			public void onLoadingStarted(String imageUri, View view)
//			{
//
//			}
//
//			@Override
//			public void onLoadingFailed(String imageUri, View view, FailReason failReason)
//			{
//
//			}
//
//			@Override
//			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage)
//			{
//				Context context = getActivity();
//				if (context != null)
//				{
//					mDetailsRow.setImageBitmap(context, loadedImage);
//				}
//				else
//				{
//					loadedImage.recycle();
//				}
//			}
//
//			@Override
//			public void onLoadingCancelled(String imageUri, View view)
//			{
//
//			}
//		});
//	}

	@OnClick(R.id.watch_button)
	public void watchButtonClicked(View view)
	{
		// TODO:
		Toast.makeText(getActivity(), "Watch button clicked!", Toast.LENGTH_SHORT).show();
	}
}
