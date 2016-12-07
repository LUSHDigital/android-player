package com.cube.lush.player.browse.channel;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v17.leanback.widget.ArrayObjectAdapter;

import com.cube.lush.player.browse.BaseMediaBrowseFragment;
import com.cube.lush.player.browse.MediaPresenter;
import com.cube.lush.player.handler.ResponseHandler;
import com.cube.lush.player.manager.MediaManager;
import com.cube.lush.player.model.CategoryContentType;
import com.cube.lush.player.model.Channel;
import com.cube.lush.player.model.MediaContent;
import com.cube.lush.player.util.MediaSorter;

import java.util.List;

/**
 * Created by tim on 06/12/2016.
 */
public class ChannelBrowseFragment extends BaseMediaBrowseFragment
{
	private Channel channel;
	private CategoryContentType contentType;
	private ArrayObjectAdapter adapter;

	public static ChannelBrowseFragment create(Channel channel, CategoryContentType contentType)
	{
		ChannelBrowseFragment channelBrowseFragment = new ChannelBrowseFragment();
		channelBrowseFragment.channel = channel;
		channelBrowseFragment.contentType = contentType;
		return channelBrowseFragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		adapter = new ArrayObjectAdapter(new MediaPresenter());
		setAdapter(adapter);
	}

	@Override
	protected void fetchData()
	{
		MediaManager.getInstance().getChannelContent(channel, contentType, new ResponseHandler<MediaContent>()
		{
			@Override public void onSuccess(@NonNull List<MediaContent> items)
			{
				setLoadingFinished(false);
				MediaSorter.MOST_RECENT_FIRST.sort(items);
				adapter.clear();
				adapter.addAll(0, items);
			}

			@Override public void onFailure(@Nullable Throwable t)
			{
				setLoadingFinished(true);
				adapter.clear();
			}
		});
	}
}
