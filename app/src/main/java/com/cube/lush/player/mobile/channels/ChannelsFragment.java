package com.cube.lush.player.mobile.channels;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cube.lush.player.content.model.Channel;
import com.cube.lush.player.R;
import com.cube.lush.player.mobile.MainActivity;
import com.cube.lush.player.mobile.channels.adapter.ChannelsAdapter;
import com.cube.lush.player.mobile.channels.listener.ChannelClickListener;
import com.cube.lush.player.mobile.content.ContentFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChannelsFragment extends Fragment implements ChannelClickListener
{
	@BindView(R.id.recycler) RecyclerView recycler;

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

		List<Channel> channels = getChannels();
		ChannelsAdapter channelsAdapter = new ChannelsAdapter(channels, this);
		recycler.setAdapter(channelsAdapter);
	}

	private List<Channel> getChannels()
	{
		return Arrays.asList(Channel.values());
	}

	@Override
	public void selectedChannel(@NonNull Channel channel)
	{
		Toast.makeText(getContext(), "Selected channel: " + channel.getTitle(), Toast.LENGTH_SHORT).show();

		((MainActivity)getActivity()).showFragment(ContentFragment.newInstance());
	}
}