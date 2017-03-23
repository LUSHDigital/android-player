package com.cube.lush.player.mobile.channels.listener;

import android.support.annotation.NonNull;

import com.cube.lush.player.content.model.Channel;

/**
 * Created by user on 23/03/2017.
 */
public interface ChannelClickListener
{
    void selectedChannel(@NonNull Channel channel);
}