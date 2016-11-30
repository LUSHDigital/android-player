package com.cube.lush.player;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
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
import android.widget.Toast;

import com.cube.lush.player.handler.ResponseHandler;
import com.cube.lush.player.manager.MediaManager;
import com.cube.lush.player.manager.SearchManager;
import com.cube.lush.player.model.Programme;
import com.cube.lush.player.model.SearchResult;
import com.cube.lush.player.presenter.SearchResultPresenter;

import java.io.Serializable;
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
	private ArrayObjectAdapter searchAdapter;

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
		searchAdapter = new ArrayObjectAdapter(new SearchResultPresenter());

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
		search(newQuery);
		return true;
	}

	@Override
	public boolean onQueryTextSubmit(String query)
	{
		return false;
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
				searchAdapter.clear();
				searchAdapter.addAll(0, items);

				ListRow searchRow = new ListRow(new HeaderItem("Search Results"), searchAdapter);
				mRowsAdapter.clear();
				mRowsAdapter.add(searchRow);
			}

			@Override public void onFailure(@Nullable Throwable t)
			{
				searchAdapter.clear();
				mRowsAdapter.clear();
			}
		});
	}

	@Override
	public void onItemClicked(Presenter.ViewHolder itemViewHolder, final Object item, RowPresenter.ViewHolder rowViewHolder, Row row)
	{
		final Context context = itemViewHolder.view.getContext();
		SearchResult searchResult = (SearchResult)item;

		MediaManager.getInstance().getProgramme(searchResult.getId(), new ResponseHandler<Programme>()
		{
			@Override public void onSuccess(@NonNull List<Programme> items)
			{
				if (items.isEmpty())
				{
					return;
				}

				Intent intent = new Intent(context, MediaDetailsActivity.class);
				intent.putExtra(MediaDetailsActivity.EXTRA_MEDIA, (Serializable)items.get(0));
				startActivity(intent);
			}

			@Override public void onFailure(@Nullable Throwable t)
			{
				Toast.makeText(context, "Error retrieving video", Toast.LENGTH_SHORT).show();
			}
		});
	}
}
