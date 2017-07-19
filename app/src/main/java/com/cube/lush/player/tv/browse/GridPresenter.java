package com.cube.lush.player.tv.browse;

import android.support.v17.leanback.widget.VerticalGridPresenter;

/**
 * Custom extension of Leanback's {@link GridPresenter} that enforces three equal-width columns
 * <p>
 *
 * @author Jamie Cruwys
 */
public class GridPresenter extends VerticalGridPresenter
{
	public GridPresenter()
	{
		super();
		setNumberOfColumns(3);
		setShadowEnabled(false);
	}

	@Override
	protected void initializeGridViewHolder(ViewHolder vh)
	{
		super.initializeGridViewHolder(vh);
		vh.getGridView().setColumnWidth(0); // zero to autosize columns
	}
}
