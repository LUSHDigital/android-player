package com.cube.lush.player.tv.channels;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.InvisibleRowPresenter;
import android.support.v17.leanback.widget.PageRow;

import com.cube.lush.player.R;
import com.cube.lush.player.api.model.Channel;
import com.cube.lush.player.mobile.model.ProgrammeFilterOption;
import com.cube.lush.player.tv.base.LushBrowseFragment;
import com.cube.lush.player.tv.browse.MenuFragmentFactory;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.Arrays;

/**
 * Displays content associated with a specific Lush channel.
 * <p />
 * Expects a {@link Channel} object to be passed in to its parent activity intent.
 **
 * @author Jamie Cruwys
 */
public class ChannelFragment extends LushBrowseFragment
{
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

		Target target = new Target()
		{
			@Override
			public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from)
			{
				BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
				setBadgeDrawable(bitmapDrawable);
			}

			@Override
			public void onBitmapFailed(Drawable errorDrawable) {}

			@Override
			public void onPrepareLoad(Drawable placeHolderDrawable) {}
		};

		Picasso.with(getActivity())
			.load(channel.getImage())
			.into(target);
	}

	private void initialiseData()
	{
		// Create the objects backing the main menu
		PageRow tvRow = new PageRow(new HeaderItem(getString(R.string.tv)));
		PageRow radioRow = new PageRow(new HeaderItem(getString(R.string.radio)));

		// Setup the fragment factory for the menu items
		MenuFragmentFactory fragmentFactory = new MenuFragmentFactory();
		fragmentFactory.registerFragment(tvRow, ChannelBrowseFragment.create(channel, ProgrammeFilterOption.TV));
		fragmentFactory.registerFragment(radioRow, ChannelBrowseFragment.create(channel, ProgrammeFilterOption.RADIO));
		getMainFragmentRegistry().registerFragment(PageRow.class, fragmentFactory);

		// Create and populate the main adapter
		ArrayObjectAdapter mainAdapter = new ArrayObjectAdapter(new InvisibleRowPresenter());
		mainAdapter.addAll(0, Arrays.asList(tvRow, radioRow));
		setAdapter(mainAdapter);
	}
}
