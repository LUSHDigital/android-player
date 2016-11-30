package com.cube.lush.player;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v17.leanback.widget.ArrayObjectAdapter;

import com.cube.lush.player.model.Channel;
import com.cube.lush.player.presenter.ChannelPresenter;

import java.util.Arrays;

/**
 * Created by tim on 30/11/2016.
 */
public class ChannelsFragment extends MediaBrowseFragment
{
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		ArrayObjectAdapter adapter = new ArrayObjectAdapter(new ChannelPresenter());
		adapter.addAll(0, Arrays.asList(Channel.values()));
		setAdapter(adapter);
	}
}
