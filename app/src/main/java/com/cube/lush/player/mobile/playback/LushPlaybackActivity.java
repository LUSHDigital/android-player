package com.cube.lush.player.mobile.playback;

import com.brightcove.player.view.BrightcovePlayer;

/**
 * Created by Jamie Cruwys of 3 SIDED CUBE on 18/04/2017.
 */
public class LushPlaybackActivity extends BrightcovePlayer
{
//	@Override protected void onCreate(Bundle savedInstanceState)
//	{
//		 When extending the BrightcovePlayer, we must assign the BrightcoveVideoView before
//		 entering the superclass. This allows for some stock video player lifecycle
//		 management.  Establish the video object and use it's event emitter to get important
//		 notifications and to control logging.
//		setContentView(R.layout.playback_fragment);
//		brightcoveVideoView = (BrightcoveExoPlayerVideoView) findViewById(R.id.brightcove_video_view);
//		brightcoveVideoView.setMediaController(new BrightcoveMediaController(brightcoveVideoView, R.layout.lush_brightcove_controller));
//		super.onCreate(savedInstanceState);
//
//		EventEmitter eventEmitter = brightcoveVideoView.getEventEmitter();
//		Catalog catalog = new Catalog(eventEmitter, getString(R.string.account), getString(R.string.policy));
//		catalog.findVideoByID(getString(R.string.videoId), new VideoListener() {
//
//			 Add the video found to the queue with add().
//			 Start playback of the video with start().
//			@Override
//			public void onVideo(Video video) {
//				brightcoveVideoView.add(video);
//				brightcoveVideoView.start();
//			}
//		});
//	}
}
