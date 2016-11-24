package com.cube.lush.player;

import android.os.Bundle;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.view.View;

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
