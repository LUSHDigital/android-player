package com.cube.lush.player.tv.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.lush.player.api.model.Programme;
import com.cube.lush.player.content.handler.ResponseHandler;
import com.cube.lush.player.content.repository.LatestProgrammesRepository;
import com.cube.lush.player.content.util.MediaSorter;
import com.cube.lush.player.tv.adapter.DiffingAdapter;
import com.cube.lush.player.tv.base.BaseMediaBrowseFragment;
import com.cube.lush.player.tv.browse.ProgrammePresenter;

import java.util.List;

/**
 * Fragment shown on the launch page of the app when the "Home" menu item is selected.
 *
 * @author Jamie Cruwys
 */
public class HomeFragment extends BaseMediaBrowseFragment
{
	/**
	 * Use a {@link DiffingAdapter} so the grid will smoothly update when changes occur.
	 */
	private DiffingAdapter<Programme> programmeAdapter = new DiffingAdapter<>(new ProgrammePresenter());

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		programmeAdapter.setEqualityTester(new DiffingAdapter.EqualityTester<Programme>()
		{
			@Override
			public boolean isEqual(Programme t1, Programme t2)
			{
				return t1.getId().equals(t2.getId());
			}
		});
		setAdapter(programmeAdapter);
	}

	@Override
	protected void fetchData()
	{
		LatestProgrammesRepository.getInstance(getActivity()).getItems(new ResponseHandler<Programme>()
		{
			@Override public void onSuccess(@NonNull List<Programme> items)
			{
				setLoadingFinished(false);
				MediaSorter.MOST_RECENT_FIRST.sort(items);
				programmeAdapter.setItems(items);
			}

			@Override public void onFailure(@Nullable Throwable t)
			{
				setLoadingFinished(false);
				programmeAdapter.clear();
			}
		});
	}
}
