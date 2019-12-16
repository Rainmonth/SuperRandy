package com.rainmonth.music.core.manager.impl;

import android.content.Context;
import android.os.Message;

import com.rainmonth.music.core.cache.ICacheManager;
import com.rainmonth.music.core.manager.BasePlayerManager;
import com.rainmonth.music.core.player.IPlayer;

/**
 * @author 张豪成
 * @date 2019-12-05 20:32
 */
public class RandyExoManager extends BasePlayerManager {
    @Override
    public IPlayer getPlayer() {
        return null;
    }

    @Override
    public void initPlayer(Context context, Message msg, ICacheManager cacheManager) {

    }
}
