package com.cube.lush.player;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.app.VerticalGridFragment;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;

import com.cube.lush.player.adapter.BasicMainFragmentAdapter;
import com.cube.lush.player.model.Channel;
import com.cube.lush.player.model.MediaContent;
import com.cube.lush.player.presenter.GridPresenter;

import java.io.Serializable;

/**
 * Base fragment for displaying a vertically-scrolling three-column grid of Lush content.
 * <p />
 * The fragment will automatically display {@link SpinnerFragment} as an overlay until {@link #setLoadingFinished()} has been called.
 *
 * Created by tim on 30/11/2016.
 */
public class MediaBrowseFragment extends VerticalGridFragment implements BrowseFragment.MainFragmentAdapterProvider
{
	private BrowseFragment.MainFragmentAdapter<MediaBrowseFragment> mainFragmentAdapter;

	private boolean isLoading = true;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setGridPresenter(new GridPresenter());

		setOnItemViewClickedListener(new OnItemViewClickedListener()
		{
			@Override
			public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row)
			{
				Context context = getActivity();

				if (context == null)
				{
					return;
				}

				if (item instanceof MediaContent)
				{
					Intent intent = new Intent(context, MediaDetailsActivity.class);
					intent.putExtra(MediaDetailsActivity.EXTRA_MEDIA, (Serializable) item);
					startActivity(intent);
				}
				else if (item instanceof Channel)
				{
					Intent intent = new Intent(context, ChannelActivity.class);
					intent.putExtra(ChannelActivity.EXTRA_CHANNEL, (Serializable) item);
					startActivity(intent);
				}
			}
		});
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		if (isLoading && getView() != null && getView().getId() != 0)
		{
			SpinnerFragment.show(getChildFragmentManager(), getView());
		}
	}

	@Override
	public BrowseFragment.MainFragmentAdapter<MediaBrowseFragment> getMainFragmentAdapter()
	{
		if (mainFragmentAdapter == null)
		{
			mainFragmentAdapter = new BasicMainFragmentAdapter<>(this);
		}
		return mainFragmentAdapter;
	}

	public void setLoadingFinished()
	{
		isLoading = false;
		SpinnerFragment.hide(getChildFragmentManager());
	}
}
