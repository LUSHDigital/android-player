package com.cube.lush.player.browse;

import android.support.v17.leanback.widget.VerticalGridPresenter;

/**
 * Custom extension of Leanback's {@link GridPresenter} that enforces three equal-width columns
 * <p>
 * Created by tim on 30/11/2016.
 */
public class GridPresenter extends VerticalGridPresenter
{
	public GridPresenter()
	{
		super();
		setNumberOfColumns(3);
	}

	@Override
	protected void initializeGridViewHolder(ViewHolder vh)
	{
		super.initializeGridViewHolder(vh);
		vh.getGridView().setColumnWidth(0); // zero to autosize columns
	}
}
