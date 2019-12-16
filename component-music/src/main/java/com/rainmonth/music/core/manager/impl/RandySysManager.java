package com.rainmonth.music.core.manager.impl;

import android.content.Context;
import android.os.Message;

import com.rainmonth.music.core.cache.ICacheManager;
import com.rainmonth.music.core.manager.BasePlayerManager;
import com.rainmonth.music.core.player.IPlayer;
import com.rainmonth.music.core.player.impl.RandyExoPlayer;

/**
 * 内部持有RandySysPlayer的管理器
 *
 * @author 张豪成
 * @date 2019-12-05 20:33
 */
public class RandySysManager extends BasePlayerManager {
    Context mContext;
    RandyExoPlayer mPlayer;

    @Override
    public IPlayer getPlayer() {
        return mPlayer;
    }

    @Override
    public void initPlayer(Context context, Message msg, ICacheManager cacheManager) {

    }
}
