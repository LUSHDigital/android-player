package com.cube.lush.player.mobile.channels;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cube.lush.player.content.model.Channel;
import com.cube.lush.player.mobile.R;
import com.cube.lush.player.mobile.R2;
import com.cube.lush.player.mobile.channels.adapter.ChannelsAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChannelsFragment extends Fragment
{
	@BindView(R2.id.recycler) RecyclerView recycler;

	public ChannelsFragment()
	{
		// Required empty public constructor
	}

	public static ChannelsFragment newInstance()
	{
		ChannelsFragment fragment = new ChannelsFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_channels, container, false);
		ButterKnife.bind(this, view);
		return view;
	}

	@Override public void onActivityCreated(@Nullable Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
		recycler.setLayoutManager(gridLayoutManager);

		ArrayList<Channel> channels = getChannels();
		ChannelsAdapter channelsAdapter = new ChannelsAdapter(channels);
		recycler.setAdapter(channelsAdapter);
	}

	private ArrayList<Channel> getChannels()
	{
		ArrayList<Channel> channels = new ArrayList<Channel>();

		for (Channel channel : Channel.values())
		{
			channels.add(channel);
		}

		return channels;
	}
}