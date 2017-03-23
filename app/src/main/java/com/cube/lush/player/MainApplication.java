package com.cube.lush.player;

import android.app.Application;
import android.widget.TextView;

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

		CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath("fonts/HelveticaNeueLTPro-Roman.otf")
			.setFontAttrId(R.attr.fontPath)
			.addCustomViewWithSetTypeface(TextView.class)
			.build());
	}
}