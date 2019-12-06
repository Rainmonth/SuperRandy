package com.rainmonth.music.core.player.impl;

import android.net.Uri;

import com.google.android.exoplayer2.ExoPlayer;
import com.rainmonth.music.core.player.BasePlayer;

/**
 * BasePlayer的Exo实现，内部持有ExoPlayer播放器实例
 *
 * @author 张豪成
 * @date 2019-12-05 20:24
 */
public class RandyExoPlayer extends BasePlayer {

    ExoPlayer mInternalPlayer;

    @Override
    public void prepare(Uri uri) {

    }

    @Override
    public void start() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void release() {

    }

    @Override
    public long getCurrentPosition() {
        return 0;
    }

    @Override
    public long getDuration() {
        return 0;
    }

    @Override
    public void seekTo() {

    }

    @Override
    public void getSourceInfo() {

    }
}
