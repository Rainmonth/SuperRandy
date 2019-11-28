package com.rainmonth.music.core;

import android.net.Uri;

import androidx.annotation.IntDef;

import com.google.android.exoplayer2.source.MediaSource;

/**
 * 该接口之定义播放器正常操作以及常用的判断（不定义业务逻辑）
 *
 * @author RandyZhang
 * @date 2019-11-19 23:26
 */
public interface IRandyAudioPlayer {


    //可以主动达到的状态
    int STATE_IDLE = 0;
    int STATE_PLAYING = 1;
    int STATE_RELEASED = 2;
    int STATE_PREPARING = 3;
    int STATE_PAUSED = 5;

    //定义枚举
    @IntDef({STATE_IDLE, STATE_PLAYING, STATE_RELEASED, STATE_PAUSED})
    @interface PlayerState {
    }

    /**
     * 准备播放
     *
     * @param rawResId 资源id
     */
    void prepare(int rawResId);

    void prepare(String resUrl);

    /**
     * 准备播放
     *
     * @param resUri 资源Uri
     */
    void prepare(Uri resUri);

    /**
     * 最终调用的准备播放
     *
     * @param resUri      资源uri
     * @param mediaSource 根据resUri创建的MediaSource
     */
    void prepare(Uri resUri, MediaSource mediaSource);

    /**
     * 播放
     */
    void startPlay();

    /**
     * 暂停
     */
    void pausePlay();

}
