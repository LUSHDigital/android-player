package com.cube.lush.player;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v17.leanback.app.DetailsFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.DetailsOverviewRow;
import android.support.v17.leanback.widget.FullWidthDetailsOverviewRowPresenter;

import com.cube.lush.player.model.MediaContent;
import com.cube.lush.player.presenter.MediaDetailsPresenter;

import static com.cube.lush.player.MediaDetailsActivity.EXTRA_MEDIA;

/**
 * Created by tim on 24/11/2016.
 */
public class MediaDetailsFragment extends DetailsFragment
{
	private ArrayObjectAdapter mAdapter;
	private DetailsOverviewRow mDetailsRow;

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		Object item = getActivity().getIntent().getSerializableExtra(EXTRA_MEDIA);

		if (!(item instanceof MediaContent))
		{
			getActivity().finish();
			return;
		}

		mAdapter = new ArrayObjectAdapter(new FullWidthDetailsOverviewRowPresenter(new MediaDetailsPresenter()));
		mDetailsRow = new DetailsOverviewRow(item);
		mAdapter.add(mDetailsRow);
		setAdapter(mAdapter);
	}
}
