package com.rainmonth.music.core.render.view.video.base;

import android.content.Context;

import com.rainmonth.music.core.bridge.IVideoViewMgrBridge;

/**
 * View和Mgr的交互层
 * 主要功能
 * - 获取ViewViewMgrBridge对象
 *
 * @author 张豪成
 * @date 2019-12-17 20:25
 */
public abstract class Layer4ViewMgrInteractLayout extends Layer3ScreenAdapterLayout {
    public Layer4ViewMgrInteractLayout(Context context) {
        super(context);
    }

    @Override
    protected IVideoViewMgrBridge getVideoViewMgrBridge() {
        return null;
    }
}
