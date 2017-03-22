package com.cube.lush.player.content.model;

import com.brightcove.player.model.Video;

import lombok.Data;

/**
 * Created by tim on 08/12/2016.
 */
@Data
public class VideoInfo
{
	private Video video;
	private long startTimeUtc;
	private long endTimeUtc;
}
