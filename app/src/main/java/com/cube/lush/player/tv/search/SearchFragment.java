package com.cube.lush.player.tv.search;

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
import android.support.v17.leanback.widget.ObjectAdapter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v17.leanback.widget.SpeechRecognitionCallback;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cube.lush.player.R;
import com.lush.player.api.model.Programme;
import com.cube.lush.player.content.handler.ResponseHandler;
import com.cube.lush.player.content.repository.SearchProgrammeRepository;
import com.cube.lush.player.tv.browse.ProgrammePresenter;
import com.cube.lush.player.tv.details.ProgrammeDetailsActivity;

import java.util.List;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

/**
 * Allows the user to perform a simple keyboard or voice search on Lush content.
 *
 * @author Jamie Cruwys
 */
public class SearchFragment extends android.support.v17.leanback.app.SearchFragment implements android.support.v17.leanback.app.SearchFragment.SearchResultProvider,
                                                                                               OnItemViewClickedListener
{
	private static final int REQUEST_SPEECH = 0x00000010;
	private ArrayObjectAdapter rowsAdapter;
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

		// We use a custom presenter so that we can show the results in a vertical grid type structure, as per the design requirements.
		rowsAdapter = new ArrayObjectAdapter(new SearchResultsPresenter());
		searchAdapter = new ArrayObjectAdapter(new ProgrammePresenter());

		setSearchResultProvider(this);
		setOnItemViewClickedListener(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = super.onCreateView(inflater, container, savedInstanceState);
		view.setBackgroundColor(getResources().getColor(R.color.primary));
		return view;
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
		return rowsAdapter;
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

		SearchProgrammeRepository.getInstance(getActivity()).setSearchTerm(query);
		SearchProgrammeRepository.getInstance(getActivity()).getItems(new ResponseHandler<Programme>()
		{
			@Override public void onSuccess(@NonNull List<Programme> items)
			{
				searchAdapter.clear();
				searchAdapter.addAll(0, items);

				ListRow searchRow = new ListRow(new HeaderItem(getString(R.string.search_results)), searchAdapter);
				rowsAdapter.clear();
				rowsAdapter.add(searchRow);
			}

			@Override public void onFailure(@Nullable Throwable t)
			{
				searchAdapter.clear();
				rowsAdapter.clear();
			}
		});
	}

	@Override
	public void onItemClicked(Presenter.ViewHolder itemViewHolder, final Object item, RowPresenter.ViewHolder rowViewHolder, Row row)
	{
		final Context context = itemViewHolder.view.getContext();
		Programme programme = (Programme)item;

		Intent intent = new Intent(context, ProgrammeDetailsActivity.class);
		intent.putExtra(ProgrammeDetailsActivity.EXTRA_PROGRAMME, programme);
		startActivity(intent);
	}
}
