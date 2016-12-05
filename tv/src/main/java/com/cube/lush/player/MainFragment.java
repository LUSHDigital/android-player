package com.cube.lush.player;

import android.os.Bundle;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.InvisibleRowPresenter;
import android.support.v17.leanback.widget.PageRow;
import android.widget.ImageView;

import com.cube.lush.player.factory.MenuFragmentFactory;

import java.util.Arrays;

/**
 * Landing page for the app, displaying a menu for "Home", "Live", and "Channels" on the left, and associated content on the right.
 * <p/>
 * Modifies the default BrowseFragment to use PageRows, instead of ListRows. This is to meet the design requirements that (i) only one row is displayed at once,
 * instead of the grid behaviour implemented by RowsFragment, and (ii) to enable a vertical grid style for the home and channels pages.
 * <p>
 * Created by tim on 24/11/2016.
 */
public class MainFragment extends LushBrowseFragment
{
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		initialiseMenu();
	}

	@Override
	protected void initialiseUI()
	{
		super.initialiseUI();

		// The badge is very wide compared to its height, so reduce the height a bit or it looks too big
		ImageView badge = (ImageView) getTitleView().findViewById(R.id.title_badge);
		badge.getLayoutParams().height /= 2;
	}

	private void initialiseMenu()
	{
		// Create the objects backing the main menu
		PageRow homeRow = new PageRow(new HeaderItem("Home"));
		PageRow liveRow = new PageRow(new HeaderItem("Live"));
		PageRow channelsRow = new PageRow(new HeaderItem("Channels"));

		// Setup the fragment factory for the menu items
		MenuFragmentFactory fragmentFactory = new MenuFragmentFactory();
		fragmentFactory.registerFragment(homeRow, new HomeFragment());
		fragmentFactory.registerFragment(liveRow, new LiveFragment());
		fragmentFactory.registerFragment(channelsRow, new ChannelsFragment());
		getMainFragmentRegistry().registerFragment(PageRow.class, fragmentFactory);

		// Create and populate the main adapter
		ArrayObjectAdapter mainAdapter = new ArrayObjectAdapter(new InvisibleRowPresenter());
		mainAdapter.addAll(0, Arrays.asList(homeRow, liveRow, channelsRow));
		setAdapter(mainAdapter);
	}
}
