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
import com.lush.player.api.model.Programme;
import com.cube.lush.player.mobile.MainActivity;
import com.cube.lush.player.mobile.content.adapter.ContentAdapter;
import com.cube.lush.player.mobile.details.DetailsFragment;
import com.cube.lush.player.mobile.model.ProgrammeFilterOption;
import com.lush.lib.listener.OnListItemClickListener;

import java.util.List;

/**
 * Base Content Fragment for most of the screens
 *
 * @author Jamie Cruwys
 */
public abstract class BaseContentFragment extends FilterableListingFragment<Programme, ProgrammeFilterOption> implements OnListItemClickListener<Programme>
{
	@NonNull abstract public String provideContentTitle();

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = super.onCreateView(inflater, container, savedInstanceState);

		if (view != null)
		{
			TextView contentTitle = (TextView)view.findViewById(R.id.content_title);
			contentTitle.setText(provideContentTitle());
		}

		return view;
	}

	@NonNull @Override public List<ProgrammeFilterOption> provideFilterOptions()
	{
		return ProgrammeFilterOption.listValues();
	}

	@NonNull @Override public String getTitleForFilterOption(ProgrammeFilterOption filterOption)
	{
		return filterOption.getName();
	}

	@NonNull @Override public ProgrammeFilterOption provideDefaultTab()
	{
		return ProgrammeFilterOption.ALL;
	}

	@NonNull @Override public RecyclerView.LayoutManager provideLayoutManagerForFilterOption(ProgrammeFilterOption filterOption)
	{
		return new LinearLayoutManager(getContext());
	}

	@NonNull @Override public RecyclerView.Adapter provideAdapterForFilterOption(ProgrammeFilterOption filterOption, @NonNull List<Programme> items)
	{
		return new ContentAdapter(items, this);
	}

	@Nullable @Override public RecyclerView.ItemDecoration provideItemDecorationForFilterOption(ProgrammeFilterOption filterOption)
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

	@Override public void onItemClick(Programme mediaContent, View view)
	{
		((MainActivity)getActivity()).showFragment(DetailsFragment.newInstance(mediaContent));
	}
}