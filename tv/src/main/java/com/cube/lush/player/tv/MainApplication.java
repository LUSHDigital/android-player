package com.cube.lush.player.tv;

import android.app.Application;
import android.widget.TextView;

import com.cube.lush.player.R;
import com.cube.lush.player.content.LushContent;
import com.squareup.leakcanary.LeakCanary;

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

		LushContent.initialise(this);
	}
}
