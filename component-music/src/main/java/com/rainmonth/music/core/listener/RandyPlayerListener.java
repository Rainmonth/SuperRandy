package com.rainmonth.music.core.listener;

/**
 *
 * @author 张豪成
 * @date 2019-12-18 11:42
 */
public interface RandyPlayerListener {
    void onPrepared();
    void onAutoCompletion();
    void onCompletion();
    void onBufferingUpdate(int percentage);
    void onSeekComplete();
    void onError(int what, int extra);
    void onInfo(int what, int extra);
    void onVideoSizeChanged();
    void onBackFullscreen();
    void onVideoPause();
    void onVideoResume();
    void onVideoResume(boolean seek);
}
