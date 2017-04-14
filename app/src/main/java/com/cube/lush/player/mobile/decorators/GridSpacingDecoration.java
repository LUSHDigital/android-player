package com.cube.lush.player.mobile.decorators;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import lombok.AllArgsConstructor;

/**
 * Created by Jamie Cruwys.
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

		boolean isFirstRow = index < columns;
		boolean isLastRow = index >= (items - columns);

		boolean isLeftColumn = index % columns == 0;
		boolean isRightColumn = index % columns == (columns - 1);

		// Apply top spacing to all rows apart from the first
		int top = !isFirstRow ? space : 0;

		// Apply bottom spacing to all rows apart from the last
		int bottom = !isLastRow ? space : 0;

		// Apply left spacing to all columns apart from the furthest left one
		int left = !isLeftColumn ? space : 0;

		// Apply right spacing to all columns apart from the furthest right one
		int right = !isRightColumn ? space : 0;

		outRect.top = top;
		outRect.bottom = bottom;
		outRect.left = left;
		outRect.right = right;
	}
}