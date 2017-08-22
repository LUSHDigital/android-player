package com.cube.lush.player.analytics;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.cube.lush.player.BuildConfig;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import lombok.Getter;

/**
 * Tracks user interactions in the app
 *
 * @author Jamie Cruwys
 */
public class Track
{
	private static final String trackingId = BuildConfig.GOOGLE_ANALYTICS_KEY;

	@Getter private static GoogleAnalytics analytics = null;
	@Getter private static Tracker tracker = null;

	public static void initialise(@NonNull Context context)
	{
		if (!Boolean.valueOf(BuildConfig.ANALYTICS_ENABLED))
		{
			return;
		}

		if (analytics == null)
		{
			analytics = GoogleAnalytics.getInstance(context.getApplicationContext());
			analytics.setLocalDispatchPeriod(30);
		}

		if (tracker == null)
		{
			tracker = analytics.newTracker(trackingId);
			tracker.enableAdvertisingIdCollection(false);
			tracker.enableAutoActivityTracking(false);
			tracker.enableExceptionReporting(false);
			tracker.setSessionTimeout(30);

			tracker.setScreenName(null);
		}
	}

	/**
	 * Send event hit as GA event
	 *
	 * @param category The category
	 * @param action The action
	 */
	public static void event(@NonNull String category, @NonNull String action)
	{
		event(category, action, null);
	}

	/**
	 * Send event hit as GA event
	 *
	 * @param category The category
	 * @param action The action
	 * @param label The label
	 */
	public static void event(@NonNull String category, @NonNull String action, @Nullable String label)
	{
		Tracker tracker = getTracker();

		if (tracker != null)
		{
			HitBuilders.EventBuilder builder = new HitBuilders.EventBuilder(category, action);

			if (!TextUtils.isEmpty(label))
			{
				builder.setLabel(label);
			}

			tracker.send(builder.build());
		}
	}

	/**
	 * Send page hit as GA event
	 *
	 * @param pageName The name of the screen to be registered as a hit
	 */
	public static void page(@NonNull String pageName)
	{
		Tracker tracker = getTracker();

		if (tracker != null)
		{
			tracker.setScreenName(pageName);
			tracker.send(new HitBuilders.ScreenViewBuilder().build());
		}
	}
}