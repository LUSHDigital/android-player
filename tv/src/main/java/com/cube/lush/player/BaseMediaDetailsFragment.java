package com.cube.lush.player;

import android.animation.ValueAnimator;
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
import android.widget.Button;
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
public abstract class BaseMediaDetailsFragment extends BrandedFragment implements MediaDetailsFlow
{
	@BindView(R.id.progress) protected ProgressBar progressBar;
	@BindView(R.id.container) protected LinearLayout contentContainer;
	@BindView(R.id.background_image) protected ImageView backgroundImage;
	@BindView(R.id.watch_button) protected Button watchButton;
	@BindView(R.id.live_indicator) protected ImageView liveIndicator;
	@BindView(R.id.title) protected TextView title;
	@BindView(R.id.start_end_time) protected TextView startEndTime;
	@BindView(R.id.description) protected TextView description;
	@BindView(R.id.time_remaining) protected TextView timeRemaining;
	@BindView(R.id.right_side) protected LinearLayout rightSide;
	protected MediaContent mediaContent;

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
			this.mediaContent = (MediaContent)item;
			populateContentView(mediaContent);
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
					mediaContent = items.get(0);
					populateContentView(mediaContent);
				}
			}

			@Override public void onFailure(@Nullable Throwable t)
			{
				// TODO: Show error message
			}
		});
	}

	@Override public void populateContentView(@NonNull MediaContent item)
	{
		title.setText(item.getTitle());
		description.setText(item.getDescription());
	}

	@Override public void revealContentView()
	{
		makeContentVisible(true);
	}

	/**
	 * Toggles content (and progress bar) visibility
	 * @param shouldBeVisible
	 */
	private void makeContentVisible(boolean shouldBeVisible)
	{
		if (shouldBeVisible)
		{
			progressBar.setVisibility(View.GONE);
			contentContainer.setVisibility(View.VISIBLE);
		}
		else
		{
			progressBar.setVisibility(View.VISIBLE);
			contentContainer.setVisibility(View.GONE);
		}

		populateHiddenView(mediaContent);
	}

	@Override public abstract void populateHiddenView(@NonNull MediaContent item);

	@Override public void revealHiddenView()
	{
		rightSide.setPivotX(0);

		ValueAnimator anim = ValueAnimator.ofInt(rightSide.getMeasuredWidth(), 0);
		anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator valueAnimator) {
				int val = (Integer) valueAnimator.getAnimatedValue();
				ViewGroup.LayoutParams layoutParams = rightSide.getLayoutParams();
				layoutParams.width = val;
				rightSide.setLayoutParams(layoutParams);
			}
		});
		anim.setDuration(1000);
		anim.start();
	}

	@OnClick(R.id.watch_button)
	public void watchButtonClicked(View view)
	{
		// TODO:
		Toast.makeText(getActivity(), "Watch button clicked!", Toast.LENGTH_SHORT).show();
	}
}