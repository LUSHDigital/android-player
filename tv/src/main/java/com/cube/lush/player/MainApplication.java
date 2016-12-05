package com.cube.lush.player;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.squareup.leakcanary.LeakCanary;

import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * @author Jamie Cruwys
 * @project lush-player-android-client
 */
public class MainApplication extends Application
{
	@Override public void onCreate()
	{
		super.onCreate();

		// Setup crash logger
		Fabric.with(this, new Crashlytics());

		// LeakCanary must come first in onCreate
		if (LeakCanary.isInAnalyzerProcess(this)) {
			// This process is dedicated to LeakCanary for heap analysis.
			// You should not init your app in this process.
			return;
		}
		LeakCanary.install(this);

		CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath("fonts/HelveticaNeueLTPro-Roman.otf")
		                                                             .setFontAttrId(R.attr.fontPath)
		                                                             .addCustomViewWithSetTypeface(TextView.class)
		                                                             .build());

		LushContent.initialise();
		setupImageLoader();
	}

	private void setupImageLoader()
	{
		DisplayImageOptions imageOptions = new DisplayImageOptions.Builder()
			.resetViewBeforeLoading(true)
			.cacheInMemory(true)
			.cacheOnDisk(true)
			.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
			.build();

		ImageLoaderConfiguration imageLoaderConfiguration = new ImageLoaderConfiguration.Builder(this)
			.writeDebugLogs()
			.defaultDisplayImageOptions(imageOptions)
			.build();

		ImageLoader.getInstance().init(imageLoaderConfiguration);
	}

	public static int getStandardCardWidth(@NonNull Context context)
	{
		return (int)getPixelsFromDensityPixels(context, 250);
	}

	public static int getStandardImageHeight(@NonNull Context context)
	{
		// 125dp for image
		return (int)getPixelsFromDensityPixels(context, 125);
	}

	public static int getStandardCardHeight(@NonNull Context context)
	{
		// 75dp for text below image
		return (int)getPixelsFromDensityPixels(context, 200);
	}

	private static float getPixelsFromDensityPixels(@NonNull Context context, float densityPixels)
	{
		Resources resources = context.getResources();

		if (resources != null)
		{
			return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, densityPixels, resources.getDisplayMetrics());
		}

		return 0;
	}
}
