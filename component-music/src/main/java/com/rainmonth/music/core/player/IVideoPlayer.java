package com.rainmonth.music.core.player;

import com.rainmonth.music.core.player.IPlayer;

/**
 * @author 张豪成
 * @date 2019-12-05 20:03
 */
public interface IVideoPlayer extends IPlayer {
    /**
     * 获取视频的宽
     *
     * @return
     */
    int getVideoWidth();

    /**
     * 获取视频的高
     *
     * @return
     */
    int getVideoHeight();
}
