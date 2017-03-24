package com.cube.lush.player.mobile.content;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cube.lush.player.R;
import com.cube.lush.player.api.model.MediaContent;
import com.cube.lush.player.content.handler.ResponseHandler;
import com.cube.lush.player.content.manager.MediaManager;
import com.cube.lush.player.content.model.CategoryContentType;
import com.cube.lush.player.content.model.Channel;
import com.cube.lush.player.content.util.MediaSorter;
import com.cube.lush.player.mobile.MainActivity;
import com.cube.lush.player.mobile.channels.adapter.ChannelsAdapter;
import com.cube.lush.player.mobile.content.adapter.ContentAdapter;
import com.cube.lush.player.mobile.content.listener.ContentClickListener;
import com.cube.lush.player.tv.state.SpinnerFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContentFragment extends Fragment implements ContentClickListener
{
	@BindView(R.id.recycler) RecyclerView recycler;
	private ContentAdapter contentAdapter;

	public ContentFragment()
	{
		// Required empty public constructor
	}

	public static ContentFragment newInstance()
	{
		ContentFragment fragment = new ContentFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_content, container, false);
		ButterKnife.bind(this, view);
		return view;
	}

	@Override public void onActivityCreated(@Nullable Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
		recycler.setLayoutManager(linearLayoutManager);

		contentAdapter = new ContentAdapter(new ArrayList<MediaContent>(), this);
		recycler.setAdapter(contentAdapter);
	}

	@Override public void onResume()
	{
		super.onResume();

		final MainActivity mainActivity = ((MainActivity)getActivity());
		mainActivity.showLoading();

		MediaManager.getInstance().getChannelContent(Channel.LUSH_LIFE, CategoryContentType.TV, new ResponseHandler<MediaContent>()
		{
			@Override public void onSuccess(@NonNull List<MediaContent> items)
			{
				MediaSorter.MOST_RECENT_FIRST.sort(items);
				contentAdapter.setItems(items);

				mainActivity.hideLoading();
			}

			@Override public void onFailure(@Nullable Throwable t)
			{
				contentAdapter.setItems(null);

				mainActivity.hideLoading();
				Toast.makeText(getContext(), "Error retrieving content", Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override public void selectedContent(@NonNull MediaContent mediaContent)
	{
		Toast.makeText(getContext(), "Selected media: " + mediaContent.getTitle(), Toast.LENGTH_SHORT).show();
	}
}