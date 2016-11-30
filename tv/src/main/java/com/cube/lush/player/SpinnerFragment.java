package com.cube.lush.player;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import lombok.Setter;

/**
 * @author Jamie Cruwys
 * @project lush-player-android-client
 */
public class SpinnerFragment extends Fragment
{
	@Setter private Context context;

	public static SpinnerFragment newInstance(@NonNull Context context)
	{
		SpinnerFragment spinnerFragment = new SpinnerFragment();
		spinnerFragment.setContext(context);

		return spinnerFragment;
	}

	@Override public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		context = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		ProgressBar progressBar = new ProgressBar(context);

		if (container instanceof FrameLayout)
		{
			Resources resources = context.getResources();
			int width = resources.getDimensionPixelSize(R.dimen.spinner_width);
			int height = resources.getDimensionPixelSize(R.dimen.spinner_height);
			FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(width, height, Gravity.CENTER);
			progressBar.setLayoutParams(layoutParams);
		}

		return progressBar;
	}
}