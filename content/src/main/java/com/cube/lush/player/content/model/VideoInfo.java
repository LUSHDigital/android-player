package com.cube.lush.player.content.model;

import com.brightcove.player.model.Video;

/**
 * Brightcove video wrapper
 *
 * @author Jamie Cruwys
 */
public class VideoInfo
{
	private Video video;
	private long startTimeUtc;
	private long endTimeUtc;

	public Video getVideo()
	{
		return video;
	}

	public void setVideo(Video video)
	{
		this.video = video;
	}

	public long getStartTimeUtc()
	{
		return startTimeUtc;
	}

	public void setStartTimeUtc(long startTimeUtc)
	{
		this.startTimeUtc = startTimeUtc;
	}

	public long getEndTimeUtc()
	{
		return endTimeUtc;
	}

	public void setEndTimeUtc(long endTimeUtc)
	{
		this.endTimeUtc = endTimeUtc;
	}
}