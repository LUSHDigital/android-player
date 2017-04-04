package com.cube.lush.player.mobile.content;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.cube.lush.player.R;
import com.cube.lush.player.api.model.MediaContent;
import com.cube.lush.player.content.handler.ResponseHandler;
import com.cube.lush.player.content.manager.MediaManager;
import com.cube.lush.player.content.model.CategoryContentType;
import com.cube.lush.player.content.model.Channel;
import com.cube.lush.player.content.util.MediaSorter;
import com.cube.lush.player.mobile.MainActivity;
import com.cube.lush.player.mobile.base.BaseAdapter;
import com.cube.lush.player.mobile.base.ListDataRetrieval;
import com.cube.lush.player.mobile.base.ListingFragment;
import com.cube.lush.player.mobile.base.RecyclerViewClickedListener;
import com.cube.lush.player.mobile.content.adapter.ContentAdapter;
import com.cube.lush.player.mobile.content.listener.ContentClickListener;
import com.cube.lush.player.mobile.details.DetailsFragment;

import java.util.ArrayList;
import java.util.List;

public class ContentFragment extends ListingFragment implements RecyclerViewClickedListener<MediaContent>
{
	@SuppressWarnings("HardCodedStringLiteral")
	private static final String ARG_CHANNEL = "arg_channel";

	private Channel channel;

	public ContentFragment()
	{
		// Required empty public constructor
	}

	public static ContentFragment newInstance(@NonNull Channel channel)
	{
		ContentFragment fragment = new ContentFragment();
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

	@NonNull @Override protected RecyclerView.LayoutManager provideLayoutManager()
	{
		return new LinearLayoutManager(getContext());
	}

	@NonNull @Override protected BaseAdapter provideAdapter()
	{
		return new ContentAdapter(new ArrayList<MediaContent>(), this);
	}

	@Override protected void getListData(@NonNull final ListDataRetrieval callback)
	{
		MediaManager.getInstance().getChannelContent(channel, CategoryContentType.TV, new ResponseHandler<MediaContent>()
		{
			@Override public void onSuccess(@NonNull List<MediaContent> items)
			{
				MediaSorter.MOST_RECENT_FIRST.sort(items);
				callback.onListDataRetrieved(items);
			}

			@Override public void onFailure(@Nullable Throwable t)
			{
				callback.onListDataRetrievalError(t);
			}
		});
	}

	@Override public void onRecyclerViewItemClicked(@NonNull MediaContent mediaContent)
	{
		Toast.makeText(getContext(), getString(R.string.media_selected, mediaContent.getTitle()), Toast.LENGTH_SHORT).show();
		((MainActivity)getActivity()).showFragment(DetailsFragment.newInstance(mediaContent));
	}
}