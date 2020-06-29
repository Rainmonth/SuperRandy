package com.rainmonth.music.core.player;

import android.net.Uri;

import com.google.android.exoplayer2.source.MediaSource;

/**
 * @author RandyZhang
 * @date 2020/6/29 1:48 PM
 */
public interface IRandyAudioPlayer {
    int STATE_IDLE = 1;
    int STATE_PREPARING = 2;
    int STATE_BUFFERING = 3;
    int STATE_READY = 4;
    int STATE_ENDED = 5;
    int STATE_RELEASED = 6;
    int STATE_PLAYING = 7;
    int STATE_PAUSED = 8;

    void prepare(int rawResId);

    void prepare(String resUrl);

    void prepare(Uri resUri);

    void prepare(Uri resUri, MediaSource mediaSource);

    void startPlay();

    void pausePlay();
}
