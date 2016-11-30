package com.cube.lush.player.presenter;

import android.support.v17.leanback.widget.VerticalGridPresenter;

/**
 * Created by tim on 30/11/2016.
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
	    vh.getGridView().setColumnWidth(0); // autosize columns
    }
}
