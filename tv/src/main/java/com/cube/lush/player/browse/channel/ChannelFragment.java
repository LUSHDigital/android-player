package com.cube.lush.player.browse.channel;

import android.os.Bundle;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.InvisibleRowPresenter;
import android.support.v17.leanback.widget.PageRow;

import com.cube.lush.player.browse.LushBrowseFragment;
import com.cube.lush.player.browse.MenuFragmentFactory;
import com.cube.lush.player.model.CategoryContentType;
import com.cube.lush.player.model.Channel;

import java.util.Arrays;

/**
 * Displays content associated with a specific Lush channel.
 * <p />
 * Expects a {@link Channel} object to be passed in to its parent activity intent.
 *
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
		// Create the objects backing the main menu
		PageRow tvRow = new PageRow(new HeaderItem("TV"));
		PageRow radioRow = new PageRow(new HeaderItem("Radio"));

		// Setup the fragment factory for the menu items
		MenuFragmentFactory fragmentFactory = new MenuFragmentFactory();
		fragmentFactory.registerFragment(tvRow, ChannelBrowseFragment.create(channel, CategoryContentType.TV));
		fragmentFactory.registerFragment(radioRow, ChannelBrowseFragment.create(channel, CategoryContentType.RADIO));
		getMainFragmentRegistry().registerFragment(PageRow.class, fragmentFactory);

		// Create and populate the main adapter
		ArrayObjectAdapter mainAdapter = new ArrayObjectAdapter(new InvisibleRowPresenter());
		mainAdapter.addAll(0, Arrays.asList(tvRow, radioRow));
		setAdapter(mainAdapter);
	}
}
