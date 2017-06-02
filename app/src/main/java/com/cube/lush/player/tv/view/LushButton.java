package com.cube.lush.player.tv.view;

import android.animation.TimeAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.support.v17.leanback.graphics.ColorOverlayDimmer;
import android.support.v17.leanback.widget.ShadowOverlayHelper;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;

/**
 * Custom extension of Button that provides nicer focusing behaviour (dims and undims)
 *
 * @author
 */
public class LushButton extends Button
{
	/**
	 * This is mostly based on a non-public Leanback class of the same name.
	 */
	static class FocusAnimator implements TimeAnimator.TimeListener
	{
		private final View mView;
		private final int mDuration;
		private float mFocusLevel = 0f;
		private float mFocusLevelStart;
		private float mFocusLevelDelta;
		private final TimeAnimator mAnimator = new TimeAnimator();
		private final Interpolator mInterpolator = new AccelerateDecelerateInterpolator();
		private final ColorOverlayDimmer mDimmer;

		FocusAnimator(View view, int duration)
		{
			mView = view;
			mDuration = duration;
			mAnimator.setTimeListener(this);
			mDimmer = ColorOverlayDimmer.createDefault(view.getContext());

			animateFocus(false, true);
		}

		void animateFocus(boolean select, boolean immediate)
		{
			endAnimation();
			final float end = select ? 1 : 0;
			if (immediate)
			{
				setFocusLevel(end);
			}
			else if (mFocusLevel != end)
			{
				mFocusLevelStart = mFocusLevel;
				mFocusLevelDelta = end - mFocusLevelStart;
				mAnimator.start();
			}
		}

		void setFocusLevel(float level)
		{
			mFocusLevel = level;
			ShadowOverlayHelper.setNoneWrapperShadowFocusLevel(mView, level);
			mDimmer.setActiveLevel(level);
			int color = mDimmer.getPaint().getColor();
			ShadowOverlayHelper.setNoneWrapperOverlayColor(mView, color);
		}

		void endAnimation()
		{
			mAnimator.end();
		}

		@Override
		public void onTimeUpdate(TimeAnimator animation, long totalTime, long deltaTime)
		{
			float fraction;
			if (totalTime >= mDuration)
			{
				fraction = 1;
				mAnimator.end();
			}
			else
			{
				fraction = (float) (totalTime / (double) mDuration);
			}
			if (mInterpolator != null)
			{
				fraction = mInterpolator.getInterpolation(fraction);
			}
			setFocusLevel(mFocusLevelStart + fraction * mFocusLevelDelta);
		}
	}

	private FocusAnimator animator = new FocusAnimator(this, 150);

	public LushButton(Context context)
	{
		super(context);
	}

	public LushButton(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public LushButton(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
	}

	public LushButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
	{
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect)
	{
		super.onFocusChanged(focused, direction, previouslyFocusedRect);
		animator.animateFocus(focused, false);
	}
}
