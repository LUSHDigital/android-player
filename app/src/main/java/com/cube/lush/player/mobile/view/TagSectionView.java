package com.cube.lush.player.mobile.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.cube.lush.player.R;
import com.lush.player.api.model.Tag;

import java.util.List;

/**
 * <Class Description>
 *
 * @author Jamie Cruwys
 */
public class TagSectionView extends LinearLayout
{
	private TagFlowLayout tagFlowLayout;

	public TagSectionView(Context context)
	{
		super(context);
		init(context);
	}

	public TagSectionView(Context context, @Nullable AttributeSet attrs)
	{
		super(context, attrs);
		init(context);
	}

	public TagSectionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		init(context);
	}

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	public TagSectionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes)
	{
		super(context, attrs, defStyleAttr, defStyleRes);
		init(context);
	}

	private void init(Context context)
	{
		View view = inflate(context, R.layout.tag_subview, this);
		tagFlowLayout = (TagFlowLayout)view.findViewById(R.id.tag_list);
	}

	public void setTags(@NonNull List<Tag> tags)
	{
		if (tagFlowLayout != null)
		{
			if (tags.isEmpty())
			{
				tagFlowLayout.hide();
				setVisibility(View.GONE);
			}
			else
			{
				tagFlowLayout.show(tags);
				setVisibility(View.VISIBLE);
			}
		}
		else
		{
			setVisibility(View.GONE);
		}
	}

	public void setTagClickListener(@NonNull TagClickListener listener)
	{
		if (tagFlowLayout != null)
		{
			tagFlowLayout.setOnTagClickListener(listener);
		}
	}
}