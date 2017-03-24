package com.cube.lush.player.mobile.content.listener;

import android.support.annotation.NonNull;

import com.cube.lush.player.api.model.MediaContent;
import com.cube.lush.player.content.model.Channel;

/**
 * Created by user on 23/03/2017.
 */
public interface ContentClickListener
{
    void selectedContent(@NonNull MediaContent mediaContent);
}