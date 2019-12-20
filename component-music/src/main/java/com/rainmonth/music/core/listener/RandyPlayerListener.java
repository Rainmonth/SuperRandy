package com.rainmonth.music.core.listener;

/**
 * @author 张豪成
 * @date 2019-12-18 11:42
 */
public interface RandyPlayerListener {
    void onPrepared();

    /**
     * 播放自动完成注意与{@link RandyPlayerListener#onCompletion()}区别
     */
    void onAutoCompletion();

    /**
     * 播放完成{@link RandyPlayerListener#onAutoCompletion()}区别
     */
    void onCompletion();

    /**
     * 由播放控制层{@link com.rainmonth.music.core.render.view.video.base.Layer2PlayerControlLayout}
     * 实现
     *
     * @param percentage 缓冲进度
     */
    void onBufferingUpdate(int percentage);

    void onSeekComplete();

    void onError(int what, int extra);

    void onInfo(int what, int extra);

    void onVideoSizeChanged();

    /**
     * 用播放具体的播放器视图实现
     */
    void onBackFullscreen();

    void onVideoPause();

    void onVideoResume();

    void onVideoResume(boolean seek);
}
