package com.rainmonth.music.core.player.impl;

import android.content.Context;
import android.net.Uri;
import android.view.Surface;
import android.view.SurfaceHolder;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.rainmonth.music.core.helper.ExoHelper;
import com.rainmonth.music.core.player.BasePlayer;

import java.util.Map;

/**
 * BasePlayer的Exo实现，内部持有ExoPlayer播放器实例
 * ExoPlayer 播放的几大要素：
 * 1. MediaSource
 * 2. Renderer
 * 3. TrackSelector
 * 4. LoadControl
 *
 * @author 张豪成
 * @date 2019-12-05 20:24
 */
public class RandyExoPlayer extends BasePlayer {

    ExoPlayer mInternalPlayer;
    private Context mAppContext;
    private ExoHelper mHelper;

    private MediaSource mMediaSource;

    public RandyExoPlayer(Context context) {
        mAppContext = context.getApplicationContext();
//        mHelper = ExoHelper
    }

    @Override
    public void setDisplay(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void setSurface(Surface surface) {

    }

    @Override
    public void setDataSource(Context context, Uri uri) {
        // todo 根据uri获取ExoPlayer需要的MediaSource
        mMediaSource = ExoHelper.getMediaSource(uri.getPath());
    }

    @Override
    public void setDataSource(String path) {

    }

    @Override
    public void setDataSource(Context context, Uri uri, Map<String, String> headers) {

    }

    @Override
    public void prepare() {

    }

    @Override
    public void prepareAsync() {

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
    public void seekTo(long milliseconds) {

    }

    @Override
    public void release() {

    }

    @Override
    public void reset() {

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
    public void getSourceInfo() {

    }
}
