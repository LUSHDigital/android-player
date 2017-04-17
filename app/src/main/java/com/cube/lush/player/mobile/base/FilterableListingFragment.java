package com.cube.lush.player.mobile.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.cube.lush.player.R;

import java.util.List;

import uk.co.jamiecruwys.StatefulListingFragment;
import uk.co.jamiecruwys.ViewState;
import uk.co.jamiecruwys.contracts.ListingData;

/**
 * Created by Jamie Cruwys.
 */
public abstract class FilterableListingFragment<ITEM_TYPE, FILTER_OPTION> extends StatefulListingFragment<ITEM_TYPE>
{
	@NonNull public abstract List<FILTER_OPTION> provideFilterOptions();

	public abstract void getListDataForFilterOption(@NonNull FILTER_OPTION option, @NonNull ListingData listingData);

	@NonNull public abstract String getTitleForFilterOption(FILTER_OPTION option);

	@NonNull public abstract FILTER_OPTION provideDefaultTab();

	private FILTER_OPTION chosenOption;
	private LinearLayout tabContainer;

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = super.onCreateView(inflater, container, savedInstanceState);
		tabContainer = (LinearLayout)view.findViewById(R.id.tab_container);

		FILTER_OPTION defaultOption = provideDefaultTab();
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

		chosenOption = defaultOption;
		selectOption(defaultOption);

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
				return;
			}
		}
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

	@Override protected void getListData(@NonNull ListingData listingData)
	{
		setViewState(ViewState.LOADING);
		getListDataForFilterOption(chosenOption, listingData);
	}
}
