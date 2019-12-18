package com.rainmonth.music.core.bridge;

import android.view.Surface;

/**
 * 视频播放器管理器与View桥接接口
 *
 * @author 张豪成
 * @date 2019-12-17 11:02
 */
public interface IVideoViewMgrBridge extends IBaseMgrViewBridge {
    /**
     * 设置渲染
     */
    void setDisplay(Surface holder);

    void releaseSurface(Surface surface);

    int getVideoWidth();

    int getVideoHeight();

    int getVideoSarNum();

    int getVideoSarDen();

    void setCurrentVideoWidth(int currentVideoWidth);

    void setCurrentVideoHeight(int currentVideoHeight);

    int getCurrentVideoWidth();

    int getCurrentVideoHeight();

    /**
     * Surface是否支持外部lockCanvas，来自定义暂停时的绘制画面
     * exoPlayer目前不支持，因为外部lock后，切换surface会导致异常
     */
    boolean isSurfaceSupportLockCanvas();
}
