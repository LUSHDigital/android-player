package com.cube.lush.player.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v17.leanback.widget.ImageCardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cube.lush.player.R;

/**
 * Created by tim on 01/12/2016.
 */
public class CardView extends ImageCardView
{
	private static final int BORDER_WIDTH = 2;

	private GradientDrawable backgroundDrawable;
	private boolean isSelectionBorderEnabled = true;
	private TextView contentTypeTextView;

	public CardView(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		setup();
	}

	public CardView(Context context)
	{
		super(context);
		setup();
	}

	public CardView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		setup();
	}

	public void setSelectionBorderEnabled(boolean isSelectionBorderEnabled)
	{
		this.isSelectionBorderEnabled = isSelectionBorderEnabled;
	}

	private void setup()
	{
		// Set title text to occupy two lines
		View titleView = findViewById(R.id.title_text);

		if (titleView instanceof TextView)
		{
			((TextView) titleView).setMaxLines(2);
		}

		// Setup the bordered background
		setPadding(BORDER_WIDTH, BORDER_WIDTH, BORDER_WIDTH, BORDER_WIDTH);
		backgroundDrawable = new GradientDrawable();
		backgroundDrawable.setColor(Color.TRANSPARENT);
		backgroundDrawable.setStroke(BORDER_WIDTH, Color.TRANSPARENT);
		setBackground(backgroundDrawable);

		// Setup the content type text view
		ViewGroup infoArea = (ViewGroup) findViewById(R.id.info_field);
		contentTypeTextView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.card_view_themed_media, infoArea, false);
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(contentTypeTextView.getLayoutParams());
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		infoArea.addView(contentTypeTextView, 0, layoutParams);

		RelativeLayout.LayoutParams titleLayoutParams = (RelativeLayout.LayoutParams) titleView.getLayoutParams();
		titleLayoutParams.addRule(RelativeLayout.BELOW, contentTypeTextView.getId());
		titleView.setLayoutParams(titleLayoutParams);
	}

	public void setMediaText(String text)
	{
		if (contentTypeTextView == null)
		{
			return;
		}
		contentTypeTextView.setText(text);
	}

	@Override
	public void setSelected(boolean selected)
	{
		super.setSelected(selected);
		backgroundDrawable.setStroke(BORDER_WIDTH, selected && isSelectionBorderEnabled ? Color.WHITE : Color.TRANSPARENT);
	}
}
