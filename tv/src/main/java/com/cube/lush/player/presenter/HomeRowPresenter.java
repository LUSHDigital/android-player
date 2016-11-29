package com.cube.lush.player.presenter;

import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.RowPresenter;
import android.view.View;

/**
 * Created by tim on 29/11/2016.
 */
public class HomeRowPresenter extends ListRowPresenter
{
	public HomeRowPresenter()
	{
		setNumRows(2);
	}

	@Override
	protected void onRowViewExpanded(RowPresenter.ViewHolder holder, boolean expanded)
	{
		super.onRowViewExpanded(holder, expanded);
		holder.view.setVisibility(!holder.isSelected() ? View.INVISIBLE : View.VISIBLE);
	}

	@Override
	protected void onRowViewSelected(RowPresenter.ViewHolder holder, boolean selected)
	{
		super.onRowViewSelected(holder, selected);
		holder.view.setVisibility(!holder.isSelected() ? View.INVISIBLE : View.VISIBLE);
	}
}
