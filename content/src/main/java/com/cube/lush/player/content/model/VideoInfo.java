package com.cube.lush.player.content.model;

import com.brightcove.player.model.Video;

import lombok.Data;

/**
 * Brightcove video wrapper
 *
 * @author Jamie Cruwys
 */
@Data
public class VideoInfo
{
	private Video video;
	private long startTimeUtc;
	private long endTimeUtc;
}