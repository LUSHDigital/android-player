package com.cube.lush.player.tv.channels;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.cube.lush.player.content.handler.ResponseHandler;
import com.cube.lush.player.content.repository.ChannelProgrammesRepository;
import com.cube.lush.player.content.util.MediaSorter;
import com.cube.lush.player.mobile.model.ProgrammeFilterOption;
import com.cube.lush.player.tv.adapter.DiffingAdapter;
import com.cube.lush.player.tv.base.BaseMediaBrowseFragment;
import com.cube.lush.player.tv.browse.ProgrammePresenter;
import com.lush.player.api.model.Channel;
import com.lush.player.api.model.Programme;

import java.util.ArrayList;
import java.util.List;

/**
 * Channel Browse Fragment
 *
 * @author Jamie Cruwys
 */
public class ChannelBrowseFragment extends BaseMediaBrowseFragment
{
	private Channel channel;
	private ProgrammeFilterOption filterOption;

	/**
	 * Use a {@link DiffingAdapter} so the grid will smoothly update when changes occur.
	 */
	private DiffingAdapter<Programme> mediaAdapter = new DiffingAdapter<>(new ProgrammePresenter());

	public static ChannelBrowseFragment create(@NonNull Channel channel, @NonNull ProgrammeFilterOption contentType)
	{
		ChannelBrowseFragment channelBrowseFragment = new ChannelBrowseFragment();
		channelBrowseFragment.channel = channel;
		channelBrowseFragment.filterOption = contentType;
		return channelBrowseFragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		mediaAdapter.setEqualityTester(new DiffingAdapter.EqualityTester<Programme>()
		{
			@Override
			public boolean isEqual(Programme t1, Programme t2)
			{
				return t1.getId().equals(t2.getId());
			}
		});
		setAdapter(mediaAdapter);
	}

	@Override
	protected void fetchData()
	{
		if (channel != null && !TextUtils.isEmpty(channel.getTag()))
		{
			ChannelProgrammesRepository.getInstance(getActivity()).setChannelTag(channel.getTag());
			ChannelProgrammesRepository.getInstance(getActivity()).getItems(new ResponseHandler<Programme>()
			{
				@Override public void onSuccess(@NonNull List<Programme> items)
				{
					ChannelProgrammesRepository programmesRepository = ChannelProgrammesRepository.getInstance(getActivity());

					if (filterOption == ProgrammeFilterOption.RADIO)
					{
						items = new ArrayList<>(programmesRepository.getRadios());
					}
					else if (filterOption == ProgrammeFilterOption.TV)
					{
						items = new ArrayList<>(programmesRepository.getVideos());
					}

					ArrayList<Programme> programmesForChannel = new ArrayList<Programme>();

					for (Programme item : items)
					{
						if (item != null && !TextUtils.isEmpty(item.getChannel()) && item.getChannel().equals(channel.getTag()))
						{
							programmesForChannel.add(item);
						}
					}

					MediaSorter.MOST_RECENT_FIRST.sort(programmesForChannel);

					setLoadingFinished(false);
					mediaAdapter.setItems(programmesForChannel);
				}

				@Override public void onFailure(@Nullable Throwable t)
				{
					setLoadingFinished(true);
					mediaAdapter.clear();
				}
			});
		}
	}
}
