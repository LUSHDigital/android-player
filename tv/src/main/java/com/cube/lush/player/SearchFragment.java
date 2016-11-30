package com.cube.lush.player;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.ObjectAdapter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v17.leanback.widget.SpeechRecognitionCallback;
import android.text.TextUtils;

import com.cube.lush.player.handler.ResponseHandler;
import com.cube.lush.player.manager.SearchManager;
import com.cube.lush.player.model.SearchResult;
import com.cube.lush.player.presenter.SearchResultPresenter;

import java.util.List;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

/**
 * Created by tim on 24/11/2016.
 */
public class SearchFragment extends android.support.v17.leanback.app.SearchFragment implements android.support.v17.leanback.app.SearchFragment.SearchResultProvider,
                                                                                               OnItemViewClickedListener
{
	private static final int REQUEST_SPEECH = 0x00000010;
	private ArrayObjectAdapter mRowsAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		if (getActivity().getPackageManager().checkPermission(Manifest.permission.RECORD_AUDIO, getActivity().getPackageName()) == PERMISSION_GRANTED)
		{
			setSpeechRecognitionCallback(new SpeechRecognitionCallback()
			{
				@Override
				public void recognizeSpeech()
				{
					startActivityForResult(getRecognizerIntent(), REQUEST_SPEECH);
				}
			});
		}

		mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
		setSearchResultProvider(this);
		setOnItemViewClickedListener(this);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode)
		{
			case REQUEST_SPEECH:
			{
				switch (resultCode)
				{
					case Activity.RESULT_OK:
					{
						setSearchQuery(data, true);
						break;
					}
				}
				break;
			}
		}
	}

	@Override
	public ObjectAdapter getResultsAdapter()
	{
		return mRowsAdapter;
	}

	@Override
	public boolean onQueryTextChange(String newQuery)
	{
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String query)
	{
		search(query);
		return true;
	}

	private void search(@NonNull String query)
	{
		if (TextUtils.isEmpty(query))
		{
			return;
		}

		SearchManager.getInstance().search(query, new ResponseHandler<SearchResult>()
		{
			@Override public void onSuccess(@NonNull List<SearchResult> items)
			{
				mRowsAdapter.clear();

				ArrayObjectAdapter searchAdapter = new ArrayObjectAdapter(new SearchResultPresenter());
				searchAdapter.addAll(0, items);
				ListRow searchRow = new ListRow(new HeaderItem("Search Results"), searchAdapter);

				mRowsAdapter.add(searchRow);
			}

			@Override public void onFailure(@Nullable Throwable t)
			{
				mRowsAdapter.clear();
			}
		});
	}

	@Override
	public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row)
	{

	}
}
