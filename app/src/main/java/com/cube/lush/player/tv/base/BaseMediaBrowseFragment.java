package com.cube.lush.player.tv.base;

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

import com.lush.player.api.model.Channel;
import com.lush.player.api.model.Programme;
import com.cube.lush.player.tv.state.ErrorFragment;
import com.cube.lush.player.tv.state.SpinnerFragment;
import com.cube.lush.player.tv.adapter.BasicMainFragmentAdapter;
import com.cube.lush.player.tv.browse.GridPresenter;
import com.cube.lush.player.tv.channels.ChannelActivity;
import com.cube.lush.player.tv.details.ProgrammeDetailsActivity;

import java.io.Serializable;

/**
 * Base fragment for displaying a vertically-scrolling three-column grid of Lush content.
 * <p />
 * The fragment will automatically display {@link SpinnerFragment} as an overlay until {@link #setLoadingFinished(boolean)} has been called.
 *
 * @author Jamie Cruwys
 */
public abstract class BaseMediaBrowseFragment extends VerticalGridFragment implements BrowseFragment.MainFragmentAdapterProvider
{
	private BrowseFragment.MainFragmentAdapter<BaseMediaBrowseFragment> mainFragmentAdapter;

	private boolean isLoading = true;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		isLoading = true;
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

				if (item instanceof Programme)
				{
					Intent intent = new Intent(context, ProgrammeDetailsActivity.class);
					intent.putExtra(ProgrammeDetailsActivity.EXTRA_PROGRAMME, (Serializable) item);
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
		startLoading();
	}

	@Override
	public BrowseFragment.MainFragmentAdapter<BaseMediaBrowseFragment> getMainFragmentAdapter()
	{
		if (mainFragmentAdapter == null)
		{
			mainFragmentAdapter = new BasicMainFragmentAdapter<>(this);
		}
		return mainFragmentAdapter;
	}


	protected abstract void fetchData();

	protected void setLoadingFinished(boolean isError)
	{
		// This method is designed to be called from async methods so make sure we've not lost context since then
		if (getActivity() == null)
		{
			return;
		}

		isLoading = false;
		SpinnerFragment.hide(getChildFragmentManager());

		if (isError && getView() != null && getView().getId() != 0)
		{
			ErrorFragment.show(getChildFragmentManager(), getView(), new Runnable()
			{
				@Override
				public void run()
				{
					isLoading = true;
					ErrorFragment.hide(getChildFragmentManager());
					startLoading();
				}
			});
		}
	}

	protected void startLoading()
	{
		fetchData();
		if (isLoading && getView() != null && getView().getId() != 0)
		{
			SpinnerFragment.show(getChildFragmentManager(), getView());
		}
	}
}
