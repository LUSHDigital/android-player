package com.cube.lush.player.tv.channels;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v17.leanback.widget.ArrayObjectAdapter;

import com.cube.lush.player.api.model.Channel;
import com.cube.lush.player.content.handler.ResponseHandler;
import com.cube.lush.player.content.repository.ChannelRepository;
import com.cube.lush.player.tv.base.BaseMediaBrowseFragment;

import java.util.List;

/**
 * Fragment shown on the launch page of the app when the "Channels" menu item is selected.
 *
 * @author Jamie Cruwys
 */
public class ChannelsFragment extends BaseMediaBrowseFragment
{
	private ArrayObjectAdapter adapter = new ArrayObjectAdapter(new ChannelPresenter());

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setAdapter(adapter);
	}

	@Override
	protected void fetchData()
	{
		adapter.clear();

		ChannelRepository.INSTANCE.getItems(new ResponseHandler<Channel>()
		{
			@Override public void onSuccess(@NonNull List<Channel> items)
			{
				adapter.addAll(0, items);
				setLoadingFinished(false);
			}

			@Override public void onFailure(@Nullable Throwable t)
			{
				setLoadingFinished(false);
			}
		});
	}
}
