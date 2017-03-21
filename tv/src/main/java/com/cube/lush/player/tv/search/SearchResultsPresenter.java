package com.cube.lush.player.tv.search;

import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.RowPresenter;

/**
 * Modifies the default ListRowPresenter to render its items over a specified number of rows, to keep with the vertical grid-style used throughout the rest of
 * the app.
 * <p>
 * Created by tim on 05/12/2016.
 */
public class SearchResultsPresenter extends ListRowPresenter
{
	@Override
	protected void onBindRowViewHolder(final RowPresenter.ViewHolder holder, Object item)
	{
		super.onBindRowViewHolder(holder, item);

		if (item instanceof ListRow)
		{
			final ListRow row = (ListRow) item;
			int numItems = row.getAdapter().size();
			int numRows = (numItems + 2) / 3;
			((ListRowPresenter.ViewHolder) holder).getGridView().setNumRows(numRows);
		}
	}
}
