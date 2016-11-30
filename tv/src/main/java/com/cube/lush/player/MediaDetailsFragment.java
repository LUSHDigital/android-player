package com.cube.lush.player;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
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
import android.view.View;

import com.cube.lush.player.adapter.BasicMainFragmentAdapter;
import com.cube.lush.player.model.MediaContent;
import com.cube.lush.player.presenter.MediaDetailsPresenter;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import static com.cube.lush.player.MediaDetailsActivity.EXTRA_MEDIA;

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

		Object item = getActivity().getIntent().getSerializableExtra(EXTRA_MEDIA);

		if (!(item instanceof MediaContent))
		{
			MediaContent mediaContent = new MediaContent();
			mediaContent.setTitle("Live");
			mediaContent.setDescription("Description");
			mediaContent.setThumbnail("https://www.colourbox.com/preview/4057996-business-person-holding-document-file.jpg");
			mediaContent.setId("aakak");
			item = mediaContent;
		}

		mDetailsRow = new DetailsOverviewRow(item);

		// Setup presenters
		mMediaDetailsPresenter = new MediaDetailsPresenter();
		mDetailsOverviewRowPresenter = new FullWidthDetailsOverviewRowPresenter(mMediaDetailsPresenter);
		mDetailsOverviewRowPresenter.setOnActionClickedListener(this);

		// Adapters
		mActionsAdapter.add(new PlaybackControlsRow.PlayPauseAction(getActivity()));
		mDetailsRow.setActionsAdapter(mActionsAdapter);
		mAdapter = new ArrayObjectAdapter(mDetailsOverviewRowPresenter);
		mAdapter.add(mDetailsRow);
		setAdapter(mAdapter);

		ImageLoader.getInstance().loadImage(((MediaContent) item).getThumbnail(), new ImageLoadingListener()
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
				mDetailsRow.setImageBitmap(getActivity(), loadedImage);
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
