package com.cube.lush.player;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brightcove.player.analytics.Analytics;
import com.brightcove.player.media.DeliveryType;
import com.brightcove.player.model.Video;
import com.brightcove.player.view.BaseVideoView;
import com.brightcove.player.view.BrightcovePlayerFragment;

/**
 * Created by tim on 24/11/2016.
 */
public class PlaybackFragment extends BrightcovePlayerFragment
{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_playback, container);
		brightcoveVideoView = (BaseVideoView) view.findViewById(R.id.brightcove_video_view);
		Analytics analytics = brightcoveVideoView.getAnalytics();
		analytics.setAccount(getResources().getString(R.string.brightcove_account_id));

	    brightcoveVideoView.add(Video.createVideo("http://solutions.brightcove.com/bcls/assets/videos/Bird_Titmouse.mp4", DeliveryType.MP4));
	    brightcoveVideoView.start();

		super.onCreateView(inflater, container, savedInstanceState);

		return view;
	}
}
