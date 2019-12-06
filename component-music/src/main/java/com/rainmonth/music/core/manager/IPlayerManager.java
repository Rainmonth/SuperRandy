package com.rainmonth.music.core.manager;

import com.rainmonth.music.core.player.IPlayer;

/**
 * 播放器管理
 *
 * @author 张豪成
 * @date 2019-12-05 19:58
 */
public interface IPlayerManager {

    IPlayer getPlayer();

    void initPlayer();
}
