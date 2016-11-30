package com.cube.lush.player;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.app.DetailsFragment;
import android.support.v17.leanback.widget.Action;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.DetailsOverviewRow;
import android.support.v17.leanback.widget.FullWidthDetailsOverviewRowPresenter;
import android.support.v17.leanback.widget.OnActionClickedListener;
import android.support.v17.leanback.widget.PlaybackControlsRow;
import android.support.v17.leanback.widget.Presenter;
import android.text.TextUtils;
import android.view.View;

import com.cube.lush.player.adapter.BasicMainFragmentAdapter;
import com.cube.lush.player.handler.ResponseHandler;
import com.cube.lush.player.manager.MediaManager;
import com.cube.lush.player.model.MediaContent;
import com.cube.lush.player.model.Programme;
import com.cube.lush.player.presenter.MediaDetailsPresenter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

import static com.cube.lush.player.MediaDetailsActivity.EXTRA_MEDIA;
import static com.cube.lush.player.MediaDetailsActivity.EXTRA_MEDIA_ID;

/**
 * Created by tim on 24/11/2016.
 */
public class MediaDetailsFragment extends DetailsFragment implements OnActionClickedListener, BrowseFragment.MainFragmentAdapterProvider
{
	private BrowseFragment.MainFragmentAdapter<MediaDetailsFragment> mMainFragmentAdapter;
	private DetailsOverviewRow mDetailsRow;
	// Presenters
	private Presenter mMediaDetailsPresenter;
	private FullWidthDetailsOverviewRowPresenter mDetailsOverviewRowPresenter;
	// Adapters
	private ArrayObjectAdapter mAdapter = new ArrayObjectAdapter(mDetailsOverviewRowPresenter);
	private ArrayObjectAdapter mActionsAdapter = new ArrayObjectAdapter();

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		// Setup presenters
		mMediaDetailsPresenter = new MediaDetailsPresenter();
		mDetailsOverviewRowPresenter = new FullWidthDetailsOverviewRowPresenter(mMediaDetailsPresenter);
		mDetailsOverviewRowPresenter.setOnActionClickedListener(MediaDetailsFragment.this);

		mActionsAdapter.add(new PlaybackControlsRow.PlayPauseAction(getActivity()));

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

		final SpinnerFragment spinnerFragment = SpinnerFragment.newInstance(getActivity());

		getFragmentManager().beginTransaction().add(R.id.details_rows_dock, spinnerFragment).commit();

		MediaManager.getInstance().getProgramme(mediaId, new ResponseHandler<Programme>()
		{
			@Override public void onSuccess(@NonNull List<Programme> items)
			{
				getFragmentManager().beginTransaction().remove(spinnerFragment).commit();

				if (!items.isEmpty())
				{
					loadMediaContent(items.get(0));
				}
			}

			@Override public void onFailure(@Nullable Throwable t)
			{
				getFragmentManager().beginTransaction().remove(spinnerFragment).commit();
			}
		});
	}

	private void loadMediaContent(@NonNull MediaContent mediaContent)
	{
		mDetailsRow = new DetailsOverviewRow(mediaContent);

		// Adapters
		mDetailsRow.setActionsAdapter(mActionsAdapter);
		mAdapter = new ArrayObjectAdapter(mDetailsOverviewRowPresenter);
		mAdapter.add(mDetailsRow);
		setAdapter(mAdapter);

		loadImage(mediaContent);
	}

	private void loadImage(@NonNull MediaContent item)
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
				Context context = getActivity();
				if (context != null)
				{
					mDetailsRow.setImageBitmap(context, loadedImage);
				}
				else
				{
					loadedImage.recycle();
				}
			}

			@Override
			public void onLoadingCancelled(String imageUri, View view)
			{

			}
		});
	}

	@Override
	public void onActionClicked(Action action)
	{
		if (action.getId() == R.id.lb_control_play_pause)
		{
			Intent intent = new Intent(getActivity(), PlaybackActivity.class);
			startActivity(intent);
		}
	}

	@Override
	public BrowseFragment.MainFragmentAdapter<MediaDetailsFragment> getMainFragmentAdapter()
	{
		if (mMainFragmentAdapter == null)
		{
			mMainFragmentAdapter = new BasicMainFragmentAdapter<>(this);
		}
		return mMainFragmentAdapter;
	}
}
