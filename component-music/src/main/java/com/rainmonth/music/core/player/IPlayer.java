package com.rainmonth.music.core.player;

import android.net.Uri;

/**
 * 播放器差异化体现
 * 这里的提供的API可以参考{@link android.media.MediaPlayer}来提供
 *
 * @author 张豪成
 * @date 2019-12-05 20:01
 */
public interface IPlayer {

    void prepare(Uri uri);

    void start();

    void pause();

    void stop();

    void release();

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

    void seekTo();

    /**
     * 获取播放源的信息
     */
    void getSourceInfo();

    //<editor-fold> 播放器监听器设置

    /**
     * 准备完毕监听
     */
    interface OnPreparedListener {
        void onPrepare(IPlayer player);
    }

    /**
     * 缓存更新监听
     */
    interface OnBufferingUpdateListener {
        void onBufferingUpdate(IPlayer player, int percentage);
    }

    /**
     * 拖动完成监听
     */
    interface OnSeekCompleteListener {
        void onSeekComplete(IPlayer player);
    }

    /**
     * 尺寸改变监听
     */
    interface OnVideoSizeChangedListener {
        void onVideoSizeChanged(IPlayer player);
    }

    /**
     * 播放完成监听
     */
    interface OnCompletionListener {
        void onComplete(IPlayer player);
    }

    /**
     * 播放出错监听
     */
    interface OnErrorListener {
        void onError(IPlayer player, int errorCode, int extra);
    }

    //</editor-fold>
}
