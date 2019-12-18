package com.rainmonth.music.core.bridge;

import android.content.Context;

import com.rainmonth.music.core.listener.RandyPlayerListener;
import com.rainmonth.music.core.manager.IPlayerManager;

import java.io.File;
import java.util.Map;

/**
 * @author 张豪成
 * @date 2019-12-17 10:59
 */
public interface IBaseMgrViewBridge {
    RandyPlayerListener listener();

    RandyPlayerListener lastListener();

    void setListener(RandyPlayerListener listener);

    void setLastListener(RandyPlayerListener listener);

    /******************辅助方法start******************/
    void setPlayTag(String playTag);

    String getPlayTag();

    void setPlayPosition(int playPosition);

    int getPlayPosition();

    /******************辅助方法end******************/

    /**
     * 获取播放器内核管理器
     *
     * @return {@link IPlayerManager}
     */
    IPlayerManager getPlayerMgr();

    /**
     * 获取缓冲百分比
     */
    int getBufferedPercentage();

    void prepare(String url, Map<String, String> headers, boolean loop, float speed, boolean cache,
                 File cachePath, String overrideExtension);

    /**
     * 释放播放器资源
     */
    void release();

    void start();

    void stop();

    void pause();

    boolean isPlaying();

    void seekTo(long time);

    long getCurrentPosition();

    long getDuration();

    /**
     * 获取之前的状态
     */
    int getLastState();

    /**
     * 获取网络速度
     */
    long getNetSpeed();

    /**
     * 设置播放速度
     *
     * @param speed      播放速度
     * @param soundTouch 是否应用变声
     */
    void setSpeed(float speed, boolean soundTouch);

    /**
     * 获取Rotate选择的flag
     */
    int getRotateInfoFlag();

    //<editor-fold>缓存相关开始

    /**
     * 当前的url是否已经缓存
     */
    boolean isCurrentUrlCached();

    /**
     * 是否完全缓存到本地
     *
     * @param cacheDir 缓存目录
     * @param url      缓存的url
     */
    boolean isTotalCached(Context context, File cacheDir, String url);

    /**
     * 清除缓存
     *
     * @param cacheDir 缓存目录
     * @param url      缓存url
     */
    void clearCache(Context context, File cacheDir, String url);
    //</editor-fold>


}
