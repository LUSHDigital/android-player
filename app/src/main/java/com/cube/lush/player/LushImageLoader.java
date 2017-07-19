package com.cube.lush.player;

import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * <Class Description>
 *
 * @author Jamie Cruwys
 */
public class LushImageLoader
{
	public static void display(@NonNull String url, @NonNull ImageView imageView)
	{
		DisplayMetrics displayMetrics = imageView.getContext().getResources().getDisplayMetrics();

		float width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 360, displayMetrics);
		float height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 480, displayMetrics);

		display(url, imageView, (int)width, (int)height);
	}

	public static void display(@NonNull String url, @NonNull ImageView imageView, int widthDp, int heightDp)
	{
		Picasso.with(imageView.getContext())
			.load(url)
			.resize(widthDp, heightDp)
			.into(imageView);
	}

	public static void cancelDisplay(@NonNull ImageView imageView)
	{
		Picasso.with(imageView.getContext())
			.cancelRequest(imageView);

		imageView.setImageDrawable(null);
	}
}