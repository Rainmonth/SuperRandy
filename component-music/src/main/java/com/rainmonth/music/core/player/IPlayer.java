package com.rainmonth.music.core.player;

import android.content.Context;
import android.net.Uri;
import android.view.Surface;
import android.view.SurfaceHolder;

import java.util.Map;

/**
 * 播放器差异化体现
 * 这里的提供的API可以参考{@link android.media.MediaPlayer}来提供
 *
 * @author 张豪成
 * @date 2019-12-05 20:01
 */
public interface IPlayer {

    /**
     * 是否开启日志记录
     *
     * @return true if need log
     */
    boolean isLogEnabled();

    /**
     * {@link android.media.MediaPlayer#setDisplay(SurfaceHolder)}
     *
     * @param surfaceHolder
     */
    void setDisplay(SurfaceHolder surfaceHolder);

    /**
     * {@link android.media.MediaPlayer#setSurface(Surface)}
     *
     * @param surface
     */
    void setSurface(Surface surface);

    void setDataSource(String path);

    void setDataSource(Context context, Uri uri);

    void setDataSource(Context context, Uri uri, Map<String, String> headers);

    void prepare();

    void prepareAsync();

    /**
     * 开始播放
     */
    void start();

    /**
     * 暂停
     */
    void pause();

    /**
     * 停止
     */
    void stop();

    int getVideoWidth();

    int getVideoHeight();

    /**
     * 是否正在播放
     *
     * @return true if is playing
     */
    boolean isPlaying();

    /**
     * 拖动到制定位置
     *
     * @param milliseconds 要拖动的位置（单位：ms）
     */
    void seekTo(long milliseconds);

    /**
     * 资源释放
     */
    void release();

    /**
     * 播放器重置
     */
    void reset();

    /**
     * 获取当前播放的位置
     *
     * @return 当前播放的位置（单位：ms）
     */
    long getCurrentPosition();

    /**
     * 获取总共时长
     *
     * @return 播放源时长（单位：ms）
     */
    long getDuration();

    /**
     * 获取播放源的信息
     */
    void getSourceInfo();

    //<editor-fold> 播放器监听器设置

    /**
     * 设置准备完成监听
     */
    void setOnPreparedListener(OnPreparedListener onPreparedListener);

    /**
     * 准备完毕监听
     */
    interface OnPreparedListener {
        void onPrepared(IPlayer player);
    }

    /**
     * 设置播放完成监听
     */
    void setOnCompletionListener(OnCompletionListener onCompletionListener);

    /**
     * 播放完成监听
     */
    interface OnCompletionListener {
        void onCompletion(IPlayer player);
    }

    /**
     * 设置缓存更新监听
     */
    void setOnBufferingUpdateListener(OnBufferingUpdateListener onBufferingUpdateListener);

    /**
     * 缓存更新监听
     */
    interface OnBufferingUpdateListener {
        void onBufferingUpdate(IPlayer player, int percentage);
    }

    /**
     * 设置拖动完成监听
     */
    void setOnSeekCompleteListener(OnSeekCompleteListener onSeekCompleteListener);

    /**
     * 拖动完成监听
     */
    interface OnSeekCompleteListener {
        void onSeekComplete(IPlayer player);
    }

    /**
     * 设置尺寸改变监听
     */
    void setOnVideoSizeChangedListener(OnVideoSizeChangedListener onVideoSizeChangedListener);

    /**
     * 尺寸改变监听
     */
    interface OnVideoSizeChangedListener {
        void onVideoSizeChanged(IPlayer player);
    }

//    interface OnTimedTextListener {
//        void onTimedText(IPlayer player, )
//    }

    /**
     * 设置日志信息监听
     */
    void setOnInfoListener(OnInfoListener onInfoListener);

    /**
     * 日志信息监听
     */
    interface OnInfoListener {
        boolean onInfo(IPlayer player, int what, int extra);
    }

    /**
     * 设置播放出错监听
     */
    void setOnErrorListener(OnErrorListener onErrorListener);

    /**
     * 播放出错监听
     */
    interface OnErrorListener {
        boolean onError(IPlayer player, int errorCode, int extra);
    }

    //</editor-fold>
}
