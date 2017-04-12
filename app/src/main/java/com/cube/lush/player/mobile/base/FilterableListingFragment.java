package com.cube.lush.player.mobile.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cube.lush.player.R;

import java.util.List;

import uk.co.jamiecruwys.StatefulListingFragment;
import uk.co.jamiecruwys.ViewState;
import uk.co.jamiecruwys.contracts.ListingData;

/**
 * Created by Jamie Cruwys of 3 SIDED CUBE on 12/04/2017.
 */
public abstract class FilterableListingFragment<ITEM_TYPE, FILTER_OPTION> extends StatefulListingFragment<ITEM_TYPE>
{
	@NonNull public abstract List<FILTER_OPTION> provideFilterOptions();

	public abstract void getListDataForFilterOption(@NonNull FILTER_OPTION option, @NonNull ListingData listingData);

	@NonNull public abstract String getTitleForFilterOption(FILTER_OPTION option);

	private FILTER_OPTION chosenOption;

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = super.onCreateView(inflater, container, savedInstanceState);
		LinearLayout tabContainer = (LinearLayout)view.findViewById(R.id.tab_container);

		final ListingData callback = this;

		for (final FILTER_OPTION option : provideFilterOptions())
		{
			View itemView = inflater.inflate(R.layout.default_filter_item, tabContainer, false);
			TextView title = (TextView)itemView.findViewById(R.id.title);

			String titleString = getTitleForFilterOption(option);
			title.setText(titleString);

			itemView.setOnClickListener(new View.OnClickListener()
			{
				@Override public void onClick(View v)
				{
					chosenOption = option;
					setViewState(ViewState.LOADING);
					getListDataForFilterOption(option, callback);
				}
			});

			tabContainer.addView(itemView);
		}

		return view;
	}

	@Override public int provideLayout()
	{
		return R.layout.default_filter_listing;
	}

	@Override public int provideStatefulViewId()
	{
		return R.id.statefulview;
	}

	@Override protected void getListData(@NonNull ListingData listingData)
	{
		getListDataForFilterOption(chosenOption, listingData);
	}
}
