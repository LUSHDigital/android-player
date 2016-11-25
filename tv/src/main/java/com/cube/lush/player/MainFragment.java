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

import com.cube.lush.player.model.MediaContent;
import com.cube.lush.player.model.VideoContent;
import com.cube.lush.player.presenter.MediaPresenter;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tim on 24/11/2016.
 */
public class MainFragment extends BrowseFragment
{
	private ArrayObjectAdapter mMainAdapter = new ArrayObjectAdapter(new ListRowPresenter());
	private ArrayObjectAdapter mediaAdapter;

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		initialiseUI();
		initialiseData();
		getVideos();
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
				if (item instanceof MediaContent)
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
		MediaPresenter mediaPresenter = new MediaPresenter();
		mediaAdapter = new ArrayObjectAdapter(mediaPresenter);

		mMainAdapter.add(new ListRow(new HeaderItem("Featured"), mediaAdapter));
        mMainAdapter.add(new ListRow(new HeaderItem("Most watched"), mediaAdapter));
        mMainAdapter.add(new ListRow(new HeaderItem("Trending"), mediaAdapter));
        mMainAdapter.add(new ListRow(new HeaderItem("Live"), mediaAdapter));
        mMainAdapter.add(new ListRow(new HeaderItem("Channels"), mediaAdapter));
        setAdapter(mMainAdapter);
	}

	private void getVideos()
	{
		MainApplication.getAPI().listVideos().enqueue(new Callback<List<VideoContent>>()
		{
			@Override public void onResponse(Call<List<VideoContent>> call, Response<List<VideoContent>> response)
			{
				if (response.isSuccessful())
				{
					List<VideoContent> videos = response.body();
					mediaAdapter.clear();
					mediaAdapter.addAll(0, videos);
				}
			}

			@Override public void onFailure(Call<List<VideoContent>> call, Throwable t)
			{
				mediaAdapter.clear();
			}
		});
	}
}
