package com.cube.lush.player.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v17.leanback.widget.ImageCardView;
import android.util.AttributeSet;

/**
 * Created by tim on 01/12/2016.
 */
public class CardView extends ImageCardView
{
	private static final int BORDER_WIDTH = 2;

	private GradientDrawable backgroundDrawable;
	private boolean isSelectionBorderEnabled = true;

	public CardView(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		setupBorderedBackground();
	}

	public CardView(Context context)
	{
		super(context);
		setupBorderedBackground();
	}

	public CardView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		setupBorderedBackground();
	}

	public void setSelectionBorderEnabled(boolean isSelectionBorderEnabled)
	{
		this.isSelectionBorderEnabled = isSelectionBorderEnabled;
	}

	private void setupBorderedBackground()
	{
		setPadding(BORDER_WIDTH, BORDER_WIDTH, BORDER_WIDTH, BORDER_WIDTH);
		backgroundDrawable = new GradientDrawable();
		backgroundDrawable.setColor(Color.TRANSPARENT);
		backgroundDrawable.setStroke(BORDER_WIDTH, Color.TRANSPARENT);
		setBackground(backgroundDrawable);
	}

	@Override
	public void setSelected(boolean selected)
	{
		super.setSelected(selected);
		backgroundDrawable.setStroke(BORDER_WIDTH, selected && isSelectionBorderEnabled ? Color.WHITE : Color.TRANSPARENT);
	}
}
