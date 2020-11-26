package com.rainmonth.player.gsy.render.view.listener;

import android.view.Surface;

/**
 * Surface 状态变化回调
 */
public interface IGSYSurfaceListener {
    void onSurfaceAvailable(Surface surface);

    void onSurfaceSizeChanged(Surface surface, int width, int height);

    boolean onSurfaceDestroyed(Surface surface);

    void onSurfaceUpdated(Surface surface);
}
