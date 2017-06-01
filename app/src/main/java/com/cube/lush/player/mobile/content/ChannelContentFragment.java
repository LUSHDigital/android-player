package com.cube.lush.player.mobile.content;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;

import com.cube.lush.player.R;
import com.cube.lush.player.api.model.Channel;
import com.cube.lush.player.api.model.Programme;
import com.cube.lush.player.content.handler.ResponseHandler;
import com.cube.lush.player.content.repository.ChannelProgrammesRepository;
import com.cube.lush.player.content.util.MediaSorter;
import com.cube.lush.player.mobile.base.BaseContentFragment;
import com.cube.lush.player.mobile.model.ProgrammeFilterOption;

import java.util.List;

import uk.co.jamiecruwys.contracts.ListingData;

/**
 * Channel Content Fragment
 *
 * @author Jamie Cruwys
 */
public class ChannelContentFragment extends BaseContentFragment
{
	@SuppressWarnings("HardCodedStringLiteral")
	private static final String ARG_CHANNEL = "arg_channel";

	private Channel channel;

	public ChannelContentFragment()
	{
		// Required empty public constructor
	}

	public static ChannelContentFragment newInstance(@NonNull Channel channel)
	{
		ChannelContentFragment fragment = new ChannelContentFragment();
		Bundle args = new Bundle();
		args.putSerializable(ARG_CHANNEL, channel);
		fragment.setArguments(args);
		return fragment;
	}

	@Override public void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		channel = (Channel)getArguments().getSerializable(ARG_CHANNEL);
	}

	@Override public void getListDataForFilterOption(@NonNull ProgrammeFilterOption filterOption, @NonNull final ListingData callback)
	{
		ChannelProgrammesRepository.INSTANCE.setChannelTag(channel.getTag());

		ChannelProgrammesRepository.INSTANCE.getItems(new ResponseHandler<Programme>()
		{
			@Override public void onSuccess(@NonNull List<Programme> items)
			{
				MediaSorter.MOST_RECENT_FIRST.sort(items);
				callback.onListingDataRetrieved(items);
			}

			@Override public void onFailure(@Nullable Throwable t)
			{
				callback.onListingDataError(t);
			}
		});
	}

	@NonNull @Override public String provideContentTitle()
	{
		return channel.getName();
	}

	@NonNull @Override public LinearLayoutManager provideLayoutManagerForFilterOption(ProgrammeFilterOption filterOption)
	{
		final int NUMBER_COLUMNS = getResources().getInteger(R.integer.channel_content_columns);
		return new GridLayoutManager(getContext(), NUMBER_COLUMNS);
	}

	@Override public void onSaveInstanceState(Bundle outState)
	{
		outState.putSerializable(ARG_CHANNEL, channel);
		super.onSaveInstanceState(outState);
	}
}