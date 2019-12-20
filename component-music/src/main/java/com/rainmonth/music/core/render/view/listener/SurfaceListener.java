package com.rainmonth.music.core.render.view.listener;

import android.view.Surface;

/**
 * 通用Surface监听器
 *
 * @author 张豪成
 * @date 2019-12-17 11:44
 */
public interface SurfaceListener {
    void onSurfaceCreated(Surface surface);

    void onSurfaceChanged(Surface surface, int width, int height);

    boolean onSurfaceDestroyed(Surface surface);

    void onSurfaceUpdated(Surface surface);
}
