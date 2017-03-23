package com.cube.lush.player.tv.details;

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
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cube.lush.player.api.model.MediaContent;
import com.cube.lush.player.tv.ErrorFragment;
import com.cube.lush.player.R;
import com.cube.lush.player.tv.SpinnerFragment;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Base class for showing information about a specific media item, and allows the user to play it.
 * <p />
 * The screen consists of two panels, one on the left-side showing details about the media, and the one on the right showing a preview of the video.
 * Extending classes can tweak when the left and right panels are revealed by consulting the {@link MediaDetailsFlow}.
 *
 * Created by tim on 24/11/2016.
 */
public abstract class BaseMediaDetailsFragment extends BrandedFragment implements MediaDetailsFlow
{
	@BindView(R.id.container) protected ViewGroup contentContainer;
	@BindView(R.id.left_panel) protected ViewGroup leftPanel;
	@BindView(R.id.right_panel) protected ViewGroup rightPanel;
	@BindView(R.id.background_image) protected ImageView backgroundImage;
	@BindView(R.id.play_button) protected Button playButton;
	@BindView(R.id.live_indicator) protected ImageView liveIndicator;
	@BindView(R.id.title) protected TextView title;
	@BindView(R.id.start_end_time) protected TextView startEndTime;
	@BindView(R.id.description) protected TextView description;
	@BindView(R.id.time_remaining) protected TextView timeRemaining;
	protected MediaContent mediaContent;

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_media_details, container, false);
		ButterKnife.bind(this, view);
		leftPanel.setVisibility(View.INVISIBLE);
		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		// Start a spinner
		SpinnerFragment.show(getChildFragmentManager(), contentContainer);

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

		Object item = intent.getSerializableExtra(MediaDetailsActivity.EXTRA_MEDIA);

		if (item instanceof MediaContent)
		{
			populateContentView((MediaContent) item);
		}
	}

	@Override
	public void populateContentView(MediaContent item)
	{
		// This method is designed to be called from async methods so make sure we've not lost context since then
		if (getActivity() == null)
		{
			return;
		}

		mediaContent = item;
		title.setText(item.getTitle());
		description.setText(item.getDescription());
		leftPanel.setVisibility(View.VISIBLE);
		SpinnerFragment.hide(getChildFragmentManager());
		populateHiddenView(mediaContent);
	}

	@Override
	public void populateError(final Runnable retryAction)
	{
		// This method is designed to be called from async methods so make sure we've not lost context since then
		if (getActivity() == null)
		{
			return;
		}

		SpinnerFragment.hide(getChildFragmentManager());
		ErrorFragment.show(getChildFragmentManager(), contentContainer, new Runnable()
		{
			@Override
			public void run()
			{
				ErrorFragment.hide(getChildFragmentManager());
				SpinnerFragment.show(getChildFragmentManager(), getView());
				retryAction.run();
			}
		});
	}

	/**
	 * Populates the right side content. By default, fetches any thumbnail associated with the item and reveals the panel when it is loaded.
	 *
	 * @param item that will be used to populate the view
	 */
	@Override public void populateHiddenView(@NonNull MediaContent item)
	{
		if (backgroundImage != null && !TextUtils.isEmpty(item.getThumbnail()))
		{
			Picasso.with(backgroundImage.getContext())
				.load(item.getThumbnail())
				.into(backgroundImage, new Callback()
				{
					@Override public void onSuccess()
					{
						revealHiddenView();
					}

					@Override public void onError()
					{
					}
				});
		}
	}

	/**
	 * Animates the black overlay panel displayed on the right side of the fragment so that it reveals what is behind it.
	 */
	@Override public void revealHiddenView()
	{
		rightPanel.setPivotX(0);

		ValueAnimator anim = ValueAnimator.ofInt(rightPanel.getMeasuredWidth(), 0);
		anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator valueAnimator) {
				int val = (Integer) valueAnimator.getAnimatedValue();
				ViewGroup.LayoutParams layoutParams = rightPanel.getLayoutParams();
				layoutParams.width = val;
				rightPanel.setLayoutParams(layoutParams);
			}
		});
		anim.setInterpolator(new AccelerateDecelerateInterpolator());
		anim.setDuration(1000);
		anim.start();
	}

	@OnClick(R.id.play_button)
	public abstract void playButtonClicked(View view);
}
