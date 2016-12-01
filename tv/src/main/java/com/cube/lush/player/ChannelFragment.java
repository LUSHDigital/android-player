package com.cube.lush.player;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.ClassPresenterSelector;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.InvisibleRowPresenter;
import android.support.v17.leanback.widget.PageRow;

import com.cube.lush.player.factory.MenuFragmentFactory;
import com.cube.lush.player.handler.ResponseHandler;
import com.cube.lush.player.manager.MediaManager;
import com.cube.lush.player.model.CategoryContentType;
import com.cube.lush.player.model.Channel;
import com.cube.lush.player.model.MediaContent;
import com.cube.lush.player.presenter.MediaPresenter;
import com.cube.lush.player.util.MediaSorter;

import java.util.Arrays;
import java.util.List;

/**
 * Created by tim on 24/11/2016.
 */
public class ChannelFragment extends LushBrowseFragment
{
	private ArrayObjectAdapter tvAdapter;
	private ArrayObjectAdapter radioAdapter;
	private Channel channel;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		initialiseData();
		getTVContent();
		getRadioContent();
	}

	@Override
	protected void initialiseUI()
	{
		super.initialiseUI();
		channel = (Channel) getActivity().getIntent().getSerializableExtra(ChannelActivity.EXTRA_CHANNEL);
		setBadgeDrawable(getResources().getDrawable(channel.getLogo()));
	}

	private void initialiseData()
	{
		PageRow tvRow = new PageRow(new HeaderItem("TV"));
		MediaBrowseFragment tvFragment = new MediaBrowseFragment();
		tvAdapter = new ArrayObjectAdapter(new MediaPresenter());
		tvFragment.setAdapter(tvAdapter);

		PageRow radioRow = new PageRow(new HeaderItem("Radio"));
		MediaBrowseFragment radioFragment = new MediaBrowseFragment();
		radioAdapter = new ArrayObjectAdapter(new MediaPresenter());
		radioFragment.setAdapter(radioAdapter);

		// Setup the fragment factory for the menu items
		MenuFragmentFactory fragmentFactory = new MenuFragmentFactory();
		fragmentFactory.registerFragment(tvRow, tvFragment);
		fragmentFactory.registerFragment(radioRow, radioFragment);
		getMainFragmentRegistry().registerFragment(PageRow.class, fragmentFactory);

		// Create and populate the main adapter
		ClassPresenterSelector mainPresenterSelector = new ClassPresenterSelector();
		mainPresenterSelector.addClassPresenter(PageRow.class, new InvisibleRowPresenter());
		ArrayObjectAdapter mainAdapter = new ArrayObjectAdapter(mainPresenterSelector);
		mainAdapter.addAll(0, Arrays.asList(tvRow, radioRow));
		setAdapter(mainAdapter);
	}

	private void getTVContent()
	{
		MediaManager.getInstance().getChannelContent(channel, CategoryContentType.TV, new ResponseHandler<MediaContent>()
		{
			@Override public void onSuccess(@NonNull List<MediaContent> items)
			{
				MediaSorter.MOST_RECENT_FIRST.sort(items);

				tvAdapter.clear();
				tvAdapter.addAll(0, items);
			}

			@Override public void onFailure(@Nullable Throwable t)
			{
				tvAdapter.clear();
			}
		});
	}

	private void getRadioContent()
	{
		MediaManager.getInstance().getChannelContent(channel, CategoryContentType.RADIO, new ResponseHandler<MediaContent>()
		{
			@Override public void onSuccess(@NonNull List<MediaContent> items)
			{
				MediaSorter.MOST_RECENT_FIRST.sort(items);

				radioAdapter.clear();
				radioAdapter.addAll(0, items);
			}

			@Override public void onFailure(@Nullable Throwable t)
			{
				radioAdapter.clear();
			}
		});
	}
}
