package com.cube.lush.player.mobile.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.cube.lush.player.R;
import com.lush.player.api.model.Tag;

import org.apmem.tools.layouts.FlowLayout;

import java.util.List;

/**
 * <Class Description>
 *
 * @author Jamie Cruwys
 */
public class TagFlowLayout extends FlowLayout
{
	private Context context;
	private TagClickListener listener;

	public TagFlowLayout(Context context)
	{
		super(context);
		init(context);
	}

	public TagFlowLayout(Context context, AttributeSet attributeSet)
	{
		super(context, attributeSet);
		init(context);
	}

	public TagFlowLayout(Context context, AttributeSet attributeSet, int defStyle)
	{
		super(context, attributeSet, defStyle);
		init(context);
	}

	private void init(Context context)
	{
		this.context = context;
	}

	public void setOnTagClickListener(@NonNull TagClickListener listener)
	{
		this.listener = listener;
	}

	public void show(@NonNull List<Tag> tags)
	{
		clearTags();

		LayoutInflater inflater = LayoutInflater.from(context);

		for (final Tag tag : tags)
		{
			View view = inflater.inflate(R.layout.tag_item, this, false);
			TextView text = (TextView)view.findViewById(R.id.text);
			text.setText(tag.getName());

			if (listener != null)
			{
				view.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						listener.onTagClick(tag);
					}
				});
			}

			addView(view);
		}

		setVisibility(View.VISIBLE);
	}

	public void hide()
	{
		clearTags();
		setVisibility(View.GONE);
	}

	private void clearTags()
	{
		removeAllViews();
	}
}