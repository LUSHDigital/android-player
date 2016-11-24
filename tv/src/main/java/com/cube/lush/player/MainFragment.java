package com.cube.lush.player;

import android.content.Intent;
import android.os.Bundle;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.view.View;

import java.io.Serializable;

/**
 * Created by tim on 24/11/2016.
 */
public class MainFragment extends BrowseFragment
{
	private ArrayObjectAdapter mMainAdapter = new ArrayObjectAdapter(new ListRowPresenter());

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		initialiseUI();
		initialiseData();
	}

	private void initialiseUI()
	{
		setTitle("TODO");

		setHeadersState(HEADERS_ENABLED);
		setHeadersTransitionOnBackEnabled(true);

		setBrandColor(getResources().getColor(R.color.primary));
		setSearchAffordanceColor(getResources().getColor(R.color.primary_dark));

		setOnSearchClickedListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				getActivity().onSearchRequested();
			}
		});

		setOnItemViewClickedListener(new OnItemViewClickedListener()
		{
			@Override
			public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row)
			{
				if (item instanceof Serializable)
				{
					Intent intent = new Intent(itemViewHolder.view.getContext(), MediaDetailsActivity.class);
					intent.putExtra(MediaDetailsActivity.EXTRA_MEDIA, (Serializable)item);
					startActivity(intent);
				}
			}
		});
	}

	private void initialiseData()
	{
        mMainAdapter.add(new ListRow(new HeaderItem("Featured"), new ArrayObjectAdapter()));
        mMainAdapter.add(new ListRow(new HeaderItem("Most watched"), new ArrayObjectAdapter()));
        mMainAdapter.add(new ListRow(new HeaderItem("Trending"), new ArrayObjectAdapter()));
        mMainAdapter.add(new ListRow(new HeaderItem("Live"), new ArrayObjectAdapter()));
        mMainAdapter.add(new ListRow(new HeaderItem("Channels"), new ArrayObjectAdapter()));
        setAdapter(mMainAdapter);
	}
}
