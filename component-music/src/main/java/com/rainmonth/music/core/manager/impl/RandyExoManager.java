package com.rainmonth.music.core.manager.impl;

import android.content.Context;
import android.os.Message;

import com.google.android.exoplayer2.ExoPlayer;
import com.rainmonth.music.core.cache.ICacheManager;
import com.rainmonth.music.core.manager.BasePlayerManager;
import com.rainmonth.music.core.player.IPlayer;
import com.rainmonth.music.core.player.impl.RandyExoPlayer;

/**
 *
 * @author 张豪成
 * @date 2019-12-05 20:32
 */
public class RandyExoManager extends BasePlayerManager {
    private RandyExoPlayer mExoPlayer;

    @Override
    public IPlayer getPlayer() {
        return mExoPlayer;
    }

    @Override
    public void initPlayer(Context context, Message msg, ICacheManager cacheManager) {

        notifyInitPlayerSuccess();
    }

    @Override
    public void showDisplay(Message msg) {

    }

    @Override
    public void setNeedMute(boolean isMute) {

    }

    @Override
    public void releaseSurface() {

    }

    @Override
    public void release() {

    }

    @Override
    public long getBufferedPercentage() {
        return 0;
    }

    @Override
    public long getNetSpeed() {
        return 0;
    }

    @Override
    public void setSpeedPlaying(float speed, boolean soundTouch) {

    }

    @Override
    public boolean isSurfaceSupportLockCanvas() {
        return false;
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
    public int getVideoWidth() {
        return 0;
    }

    @Override
    public int getVideoHeight() {
        return 0;
    }

    @Override
    public boolean isPlaying() {
        return false;
    }

    @Override
    public void seekTo(long time) {

    }

    @Override
    public long getCurrentPosition() {
        return 0;
    }

    @Override
    public long getDuration() {
        return 0;
    }
}
