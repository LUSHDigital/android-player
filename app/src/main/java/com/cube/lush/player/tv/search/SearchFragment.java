package com.cube.lush.player.tv.search;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cube.lush.player.R;
import com.cube.lush.player.content.handler.ResponseHandler;
import com.cube.lush.player.content.repository.SearchProgrammeRepository;
import com.cube.lush.player.tv.browse.ProgrammePresenter;
import com.cube.lush.player.tv.details.ProgrammeDetailsActivity;
import com.lush.player.api.model.Programme;

import java.util.ArrayList;
import java.util.List;

/**
 * Allows the user to perform a simple keyboard or voice search on Lush content.
 *
 * @author Jamie Cruwys
 */
public class SearchFragment extends android.support.v17.leanback.app.SearchFragment implements android.support.v17.leanback.app.SearchFragment.SearchResultProvider,
                                                                                               OnItemViewClickedListener
{
	private static final int REQUEST_SPEECH = 0x00000010;
	private static final int REQUEST_SPEECH_PERMISSIONS = 1234567890;
	private ArrayObjectAdapter rowsAdapter;
	private ArrayObjectAdapter searchAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		tryEnablingSpeechRecognition();

		// We use a custom presenter so that we can show the results in a vertical grid type structure, as per the design requirements.
		rowsAdapter = new ArrayObjectAdapter(new SearchResultsPresenter());
		searchAdapter = new ArrayObjectAdapter(new ProgrammePresenter());

		setSearchResultProvider(this);
		setOnItemViewClickedListener(this);
	}

	private void tryEnablingSpeechRecognition()
	{
		final Activity activity = getActivity();

		if (activity == null)
		{
			return;
		}

		ArrayList<String> permissionsToRequest = new ArrayList<>();

		boolean recordAudioPermissionGranted = ContextCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;

		if (!recordAudioPermissionGranted)
		{
			permissionsToRequest.add(Manifest.permission.RECORD_AUDIO);
		}

		boolean writeExternalStoragePermissionGranted = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

		if (!writeExternalStoragePermissionGranted)
		{
			permissionsToRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
		}

		if (!permissionsToRequest.isEmpty())
		{
			final String[] permissions = permissionsToRequest.toArray(new String[0]);

			if (shouldShowRequestPermissionRationale(Manifest.permission.RECORD_AUDIO) || shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE))
			{
				// Show an explanation to the user *asynchronously* -- don't block
				// this thread waiting for the user's response! After the user
				// sees the explanation, try again to request the permission.

				new AlertDialog.Builder(activity)
					.setMessage("In order to use the speech functionality you must have the record audio and write to external storage permissions enabled")
					.setPositiveButton("OK", new DialogInterface.OnClickListener()
					{
						@Override
						public void onClick(DialogInterface dialog, int which)
						{
							requestPermissions(permissions, REQUEST_SPEECH_PERMISSIONS);
						}
					})
					.setNegativeButton("Cancel", null)
					.create()
					.show();
			}
			else
			{
				requestPermissions(permissions, REQUEST_SPEECH_PERMISSIONS);
			}

			return;
		}

		if (recordAudioPermissionGranted && writeExternalStoragePermissionGranted)
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
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = super.onCreateView(inflater, container, savedInstanceState);

		if (view != null)
		{
			view.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.primary));
		}

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
						if (data != null)
						{
							setSearchQuery(data, true);
						}
						break;
					}
				}
				break;
			}
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
	{
		switch (requestCode)
		{
			case REQUEST_SPEECH_PERMISSIONS:
				tryEnablingSpeechRecognition();
				break;
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
