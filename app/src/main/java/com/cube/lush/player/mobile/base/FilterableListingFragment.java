package com.cube.lush.player.mobile.base;

import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.cube.lush.player.R;

import java.io.Serializable;
import java.util.List;

import uk.co.jamiecruwys.StatefulListingFragment;
import uk.co.jamiecruwys.ViewState;
import uk.co.jamiecruwys.contracts.ListingData;

/**
 * Filterable Listing Fragment which provides most of the functionality for statefulviews and leaves little to be implemented by the subclasses
 *
 * @author Jamie Cruwys
 */
public abstract class FilterableListingFragment<ITEM_TYPE, FILTER_OPTION extends Serializable> extends StatefulListingFragment<ITEM_TYPE>
{
	@NonNull public abstract List<FILTER_OPTION> provideFilterOptions();

	public abstract void getListDataForFilterOption(@NonNull FILTER_OPTION option, @NonNull ListingData callback);

	@NonNull public abstract String getTitleForFilterOption(FILTER_OPTION option);

	@NonNull public abstract FILTER_OPTION provideDefaultTab();

	@NonNull public abstract RecyclerView.LayoutManager provideLayoutManagerForFilterOption(FILTER_OPTION option);

	@NonNull public abstract RecyclerView.Adapter provideAdapterForFilterOption(FILTER_OPTION option, @NonNull List<ITEM_TYPE> items);

	@Nullable public abstract RecyclerView.ItemDecoration provideItemDecorationForFilterOption(FILTER_OPTION option);

	private static final String ARG_FILTER_OPTION = "filter_option";

	private FILTER_OPTION chosenOption;
	private LinearLayout tabContainer;

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = super.onCreateView(inflater, container, savedInstanceState);
		tabContainer = (LinearLayout)view.findViewById(R.id.tab_container);

		recycler.setBackgroundColor(provideBackgroundColor());
		view.setBackgroundColor(provideBackgroundColor());

		if (savedInstanceState == null)
		{
			chosenOption = provideDefaultTab();
		}
		else
		{
			chosenOption = (FILTER_OPTION)savedInstanceState.getSerializable(ARG_FILTER_OPTION);
		}

		final ListingData callback = this;

		for (final FILTER_OPTION option : provideFilterOptions())
		{
			Button itemView = (Button)inflater.inflate(R.layout.default_filter_item, tabContainer, false);

			String titleString = getTitleForFilterOption(option);
			itemView.setText(titleString);

			itemView.setOnClickListener(new View.OnClickListener()
			{
				@Override public void onClick(View view)
				{
					chosenOption = option;

					clearButtonStates();
					view.setActivated(true);

					setViewState(ViewState.LOADING);
					getListDataForFilterOption(option, callback);
				}
			});

			tabContainer.addView(itemView);
		}

		selectOption(chosenOption);

		return view;
	}

	public void selectOption(@NonNull FILTER_OPTION option)
	{
		clearButtonStates();

		String titleForOption = getTitleForFilterOption(option);

		for (int index = 0; index < tabContainer.getChildCount(); index++)
		{
			Button childView = (Button)tabContainer.getChildAt(index);

			if (childView.getText().equals(titleForOption))
			{
				childView.setActivated(true);
			}
		}

		chosenOption = option;
		getListData(this);
	}

	private void clearButtonStates()
	{
		for (int index = 0; index < tabContainer.getChildCount(); index++)
		{
			Button childView = (Button)tabContainer.getChildAt(index);
			childView.setActivated(false);
		}
	}

	@Override public int provideLayout()
	{
		return R.layout.default_filter_listing;
	}

	@Override public int provideStatefulViewId()
	{
		return R.id.statefulview;
	}

	@Override protected void getListData(@NonNull ListingData callback)
	{
	 	setViewState(ViewState.LOADING);
		getListDataForFilterOption(chosenOption, callback);
	}

	@NonNull protected RecyclerView.LayoutManager provideLayoutManager()
	{
		return provideLayoutManagerForFilterOption(chosenOption);
	}

	@NonNull protected RecyclerView.Adapter provideAdapter(@NonNull List<ITEM_TYPE> items)
	{
		return provideAdapterForFilterOption(chosenOption, items);
	}

	@Nullable protected RecyclerView.ItemDecoration provideItemDecoration()
	{
		return provideItemDecorationForFilterOption(chosenOption);
	}

	@ColorInt public int provideBackgroundColor()
	{
		return ContextCompat.getColor(getContext(), android.R.color.black);
	}

	@Override public void onSaveInstanceState(Bundle outState)
	{
		outState.putSerializable(ARG_FILTER_OPTION, chosenOption);
		super.onSaveInstanceState(outState);
	}
}