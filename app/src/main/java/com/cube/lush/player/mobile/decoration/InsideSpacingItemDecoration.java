package com.cube.lush.player.mobile.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Jamie Cruwys.
 */
public class InsideSpacingItemDecoration extends RecyclerView.ItemDecoration
{
	private int topSpace, bottomSpace, leftSpace, rightSpace;
	private int columns;

	public InsideSpacingItemDecoration(int space, int columns)
	{
		this(space, space, columns);
	}

	public InsideSpacingItemDecoration(int verticalSpace, int horizontalSpace, int columns)
	{
		this(verticalSpace, verticalSpace, horizontalSpace, horizontalSpace, columns);
	}

	public InsideSpacingItemDecoration(int topSpace, int bottomSpace, int leftSpace, int rightSpace, int columns)
	{
		this.topSpace = topSpace;
		this.bottomSpace = bottomSpace;
		this.leftSpace = leftSpace;
		this.rightSpace = rightSpace;
		this.columns = columns;
	}

	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
	{
		int index = parent.getChildAdapterPosition(view);
		int items = parent.getAdapter().getItemCount();

		boolean isFirstRow = index < columns;
		boolean isLastRow = index >= (items - columns);

		boolean isSingleColumn = columns == 1;
		boolean isLeftColumn = index % columns == 0;
		boolean isRightColumn = index % columns == (columns - 1);

		int top = 0;
		int bottom = 0;
		int left = 0;
		int right = 0;

		// Apply top spacing to all rows apart from the first
		if (!isFirstRow)
		{
			top = topSpace;
		}

		// Apply bottom spacing to all rows apart from the last
		if (!isLastRow)
		{
			bottom = bottomSpace;
		}

		// Can only space the inside if there is more than one column
		if (!isSingleColumn)
		{
			// Apply left spacing to all columns apart from the furthest left one
			if (!isLeftColumn)
			{
				left = leftSpace;
			}

			// Apply right spacing to all columns apart from the furthest right one
			if (!isRightColumn)
			{
				right = rightSpace;
			}
		}

		outRect.top = top;
		outRect.bottom = bottom;
		outRect.left = left;
		outRect.right = right;
	}
}