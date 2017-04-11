package com.cube.lush.player.mobile;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cube.lush.player.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.jamiecruwys.StatefulView;

/**
 * Created by Jamie Cruwys of 3 SIDED CUBE on 11/04/2017.
 */
public class ErrorView extends RelativeLayout
{
	@BindView(R.id.icon) ImageView icon;
	@BindView(R.id.title) TextView title;
	@BindView(R.id.description) TextView description;

	public ErrorView(Context context, AttributeSet attrs)
	{
		super(context, attrs);

		TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, uk.co.jamiecruwys.statefulview.R.styleable.StatefulView, 0, 0);

		int iconDrawable = attributes.getResourceId(R.styleable.ErrorView_icon, 0);
		String titleString = attributes.getString(R.styleable.ErrorView_title);
		String descriptionString = attributes.getString(R.styleable.ErrorView_description);

		attributes.recycle();

		if (iconDrawable == 0)
		{
			throw new RuntimeException(StatefulView.class.getSimpleName() + " must have custom attribute icon set");
		}
		else if (TextUtils.isEmpty(titleString))
		{
			throw new RuntimeException(StatefulView.class.getSimpleName() + " must have custom attribute title set");
		}
		else if (TextUtils.isEmpty(descriptionString))
		{
			throw new RuntimeException(StatefulView.class.getSimpleName() + " must have custom attribute description set");
		}

		inflate(context, R.layout.default_error_in, this);
		ButterKnife.bind(this);

		Drawable drawable = ContextCompat.getDrawable(context, iconDrawable);
		icon.setImageDrawable(drawable);

		title.setText(titleString);
		description.setText(descriptionString);
	}
}