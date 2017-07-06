package com.cube.lush.player.mobile.content;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;

import com.cube.lush.player.R;
import com.cube.lush.player.content.handler.ResponseHandler;
import com.cube.lush.player.content.repository.ChannelProgrammesRepository;
import com.cube.lush.player.content.util.MediaSorter;
import com.cube.lush.player.mobile.base.BaseContentFragment;
import com.cube.lush.player.mobile.model.ProgrammeFilterOption;
import com.lush.player.api.model.Channel;
import com.lush.player.api.model.ContentType;
import com.lush.player.api.model.Programme;

import java.util.ArrayList;
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

	@Override public void getListDataForFilterOption(@NonNull final ProgrammeFilterOption filterOption, @NonNull final ListingData callback)
	{
		ChannelProgrammesRepository.getInstance(getContext()).setChannelTag(channel.getTag());
		ChannelProgrammesRepository.getInstance(getContext()).getItems(new ResponseHandler<Programme>()
		{
			@Override public void onSuccess(@NonNull List<Programme> items)
			{
				if (filterOption == ProgrammeFilterOption.TV)
				{
					items = filterByContentType(items, ContentType.TV);
				}
				else if (filterOption == ProgrammeFilterOption.RADIO)
				{
					items = filterByContentType(items, ContentType.RADIO);
				}

				items = filterByChannel(items, channel.getTag());

				MediaSorter.MOST_RECENT_FIRST.sort(items);

				if (callback != null)
				{
					callback.onListingDataRetrieved(items);
				}
			}

			@Override public void onFailure(@Nullable Throwable t)
			{
				if (callback != null)
				{
					callback.onListingDataError(t);
				}
			}
		});
	}

	private List<Programme> filterByChannel(@NonNull List<Programme> items, @NonNull String channelName)
	{
		ArrayList<Programme> channelProgrammes = new ArrayList<>();

		for (Programme item : items)
		{
			if (item != null && !TextUtils.isEmpty(item.getChannel()) && item.getChannel().equals(channelName))
			{
				channelProgrammes.add(item);
			}
		}

		return channelProgrammes;
	}

	private List<Programme> filterByContentType(@NonNull List<Programme> items, @NonNull ContentType contentType)
	{
		ArrayList<Programme> contentTypeProgrammes = new ArrayList<>();

		for (Programme item : items)
		{
			if (item != null && item.getType() != null && item.getType() == contentType)
			{
				contentTypeProgrammes.add(item);
			}
		}

		return contentTypeProgrammes;
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