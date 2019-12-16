package com.rainmonth.music.core.manager;

import android.content.Context;
import android.os.Message;

import com.rainmonth.music.core.cache.ICacheManager;
import com.rainmonth.music.core.player.IPlayer;

/**
 * 播放器管理
 *
 * @author 张豪成
 * @date 2019-12-05 19:58
 */
public interface IPlayerManager {

    IPlayer getPlayer();

    void initPlayer(Context context, Message msg, ICacheManager cacheManager);
}
