package com.cube.lush.player.mobile.content.holder;

import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cube.lush.player.R;
import com.lush.player.api.model.ContentType;
import com.lush.player.api.model.Programme;
import com.lush.view.holder.BaseViewHolder;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Content View Holder
 *
 * @author Jamie Cruwys
 */
public class ContentViewHolder extends BaseViewHolder<Programme>
{
	@BindView(R.id.image) public ImageView image;
	@BindView(R.id.channel) public TextView channel;
	@BindView(R.id.title) public TextView title;
	@BindView(R.id.type) public ImageView type;
	@BindView(R.id.date) public TextView date;
	// @BindView(R.id.new_indicator) public TextView newIndicator;

	public ContentViewHolder(View view)
	{
		super(view);
		ButterKnife.bind(this, view);
	}

	@Override public void bind(Programme programme)
	{
		Picasso.with(image.getContext())
			.load(programme.getThumbnail())
			.fit()
			.centerCrop()
			.into(image);

		setTextOrHide(programme.getChannel(), channel);
		setTextOrHide(programme.getTitle(), title);
		setTextOrHide(programme.getRelativeDate(), date);
		setType(programme.getType(), type);
	}

	private void setTextOrHide(@Nullable String text, @NonNull TextView textView)
	{
		if (text == null || TextUtils.isEmpty(text))
		{
			textView.setText("");
			textView.setVisibility(View.GONE);
		}
		else
		{
			textView.setText(text);
			textView.setVisibility(View.VISIBLE);
		}
	}

	private void setType(@Nullable ContentType type, @NonNull ImageView imageView)
	{
		if (type == null)
		{
			imageView.setVisibility(View.GONE);
		}
		else
		{
			@DrawableRes int icon = 0;

			if (type == ContentType.TV)
			{
				icon = R.drawable.video_icon;
			}
			else if (type == ContentType.RADIO)
			{
				icon = R.drawable.radio_icon;
			}

			Drawable drawable = ContextCompat.getDrawable(imageView.getContext(), icon);

			if (drawable == null)
			{
				imageView.setVisibility(View.GONE);
			}
			else
			{
				imageView.setImageDrawable(drawable);
				imageView.setVisibility(View.VISIBLE);
			}
		}
	}
}