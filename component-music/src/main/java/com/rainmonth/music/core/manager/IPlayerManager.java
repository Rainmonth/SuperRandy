package com.rainmonth.music.core.manager;

import android.content.Context;
import android.os.Message;

import com.rainmonth.music.core.cache.ICacheManager;
import com.rainmonth.music.core.player.IPlayer;

/**
 * 播放器管理
 * 内部持有一个相应的{@link IPlayer}实例，具体操作同时通过该实例来实现的
 *
 * @author 张豪成
 * @date 2019-12-05 19:58
 */
public interface IPlayerManager {

    IPlayer getPlayer();

    void initPlayer(Context context, Message msg, ICacheManager cacheManager);

    void showDisplay(Message msg);

    void setNeedMute(boolean isMute);

    void releaseSurface();

    void release();

    long getBufferedPercentage();

    long getNetSpeed();

    void setSpeedPlaying(float speed, boolean soundTouch);

    boolean isSurfaceSupportLockCanvas();

    void start();

    void pause();

    void stop();

    int getVideoWidth();

    int getVideoHeight();

    boolean isPlaying();

    void seekTo(long time);

    long getCurrentPosition();

    long getDuration();

    interface OnPlayerInitSuccessListener {
        void onPlayerInitSuccess();
    }
}
