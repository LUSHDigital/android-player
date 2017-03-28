package com.cube.lush.player.mobile.channels.adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import lombok.AllArgsConstructor;

/**
 * Created by Jamie Cruwys of 3 SIDED CUBE on 28/03/2017.
 *
 * This class only accepts 2 columns at the moment
 */
@AllArgsConstructor
public class GridSpacingDecoration extends RecyclerView.ItemDecoration
{
	private int space;
	private int columns;

	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
	{
		int index = parent.getChildAdapterPosition(view);
		int items = parent.getAdapter().getItemCount();

		boolean isFirstRow = index < 2;
		boolean isLastRow = index >= (items - 2);

		boolean isLeftColumn = index % 2 == 0;
		boolean isRightColumn = index % 2 == 1;

		// Apply top spacing to all rows apart from the first
		int top = !isFirstRow ? space : 0;

		// Apply bottom spacing to all rows apart from the last
		int bottom = !isLastRow ? space : 0;

		// Only apply left spacing to the right column
		int left = isRightColumn ? space : 0;

		// Only apply right spacing to the left column
		int right = isLeftColumn ? space : 0;

		outRect.top = top;
		outRect.bottom = bottom;
		outRect.left = left;
		outRect.right = right;
	}
}