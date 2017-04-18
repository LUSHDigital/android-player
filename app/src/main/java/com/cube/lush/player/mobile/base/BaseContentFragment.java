package com.cube.lush.player.mobile.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cube.lush.player.R;
import com.cube.lush.player.api.model.MediaContent;
import com.cube.lush.player.content.model.CategoryContentType;
import com.cube.lush.player.mobile.MainActivity;
import com.cube.lush.player.mobile.content.adapter.ContentAdapter;
import com.cube.lush.player.mobile.details.DetailsFragment;
import com.lush.lib.listener.OnListItemClickListener;

import java.util.List;

/**
 * Created by Jamie Cruwys.
 */
public abstract class BaseContentFragment extends FilterableListingFragment<MediaContent, CategoryContentType> implements OnListItemClickListener<MediaContent>
{
	@NonNull abstract public String provideContentTitle();

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = super.onCreateView(inflater, container, savedInstanceState);

		TextView contentTitle = (TextView)view.findViewById(R.id.content_title);
		contentTitle.setText(provideContentTitle());

		return view;
	}

	@NonNull @Override public List<CategoryContentType> provideFilterOptions()
	{
		return CategoryContentType.listValues();
	}

	@NonNull @Override public String getTitleForFilterOption(CategoryContentType contentType)
	{
		return contentType.getDisplayName();
	}

	@NonNull @Override public CategoryContentType provideDefaultTab()
	{
		return CategoryContentType.ALL;
	}

	@NonNull @Override public RecyclerView.LayoutManager provideLayoutManagerForFilterOption(CategoryContentType categoryContentType)
	{
		return new LinearLayoutManager(getContext());
	}

	@NonNull @Override public RecyclerView.Adapter provideAdapterForFilterOption(CategoryContentType categoryContentType, @NonNull List<MediaContent> items)
	{
		return new ContentAdapter(items, this);
	}

	@Nullable @Override public RecyclerView.ItemDecoration provideItemDecorationForFilterOption(CategoryContentType categoryContentType)
	{
		return null;
	}

	@Override public int provideLoadingLayout()
	{
		return R.layout.content_loading;
	}

	@Override public int provideEmptyLayout()
	{
		return R.layout.content_empty;
	}

	@Override public int provideLoadedLayout()
	{
		return R.layout.content_loaded;
	}

	@Override public int provideErrorLayout()
	{
		return R.layout.content_error;
	}

	@Override public void onItemClick(MediaContent mediaContent, View view)
	{
		((MainActivity)getActivity()).showFragment(DetailsFragment.newInstance(mediaContent));
	}
}