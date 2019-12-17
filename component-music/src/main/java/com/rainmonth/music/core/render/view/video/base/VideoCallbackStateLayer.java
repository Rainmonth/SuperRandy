package com.rainmonth.music.core.render.view.video.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Surface;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rainmonth.music.core.bridge.IVideoViewMgrBridge;

/**
 * 视频回调及状态管理层
 * 实现了
 * {@link com.rainmonth.music.core.helper.MeasureHelper.MeasureFormVideoParamsListener}、
 * {@link VideoDrawLayerContainer#showPauseCover()}、
 * {@link VideoDrawLayerContainer#releasePauseCover()}等方法
 *
 * @author 张豪成
 * @date 2019-12-17 18:00
 */
public abstract class VideoCallbackStateLayer extends VideoDrawLayerContainer {

    public VideoCallbackStateLayer(Context context) {
        super(context);
        init(context);
    }

    public VideoCallbackStateLayer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public VideoCallbackStateLayer(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    protected void init(Context context) {

    }

    protected abstract IVideoViewMgrBridge getVideoViewMgrBridge();

    //<editor-fold>VideoDrawLayerContainer抽象方法实现
    @Override
    protected void showPauseCover() {

    }

    @Override
    protected void releasePauseCover() {

    }

    @Override
    protected void setSmallVideoTextureView() {

    }

    @Override
    protected void setDisplay(Surface surface) {

    }

    @Override
    protected void releaseSurface(Surface surface) {

    }
    //</editor-fold>

    //<editor-fold> MeasureHelper.MeasureFormVideoParamsListener实现
    @Override
    public int getCurrentVideoWidth() {
        if (getVideoViewMgrBridge() != null) {
            return getVideoViewMgrBridge().getVideoWidth();
        }
        return 0;
    }

    @Override
    public int getCurrentVideoHeight() {
        if (getVideoViewMgrBridge() != null) {
            return getVideoViewMgrBridge().getVideoHeight();
        }
        return 0;
    }

    @Override
    public int getVideoSarNum() {
        if (getVideoViewMgrBridge() != null) {
            return getVideoViewMgrBridge().getVideoSarNum();
        }
        return 0;
    }

    @Override
    public int getVideoSarDen() {
        if (getVideoViewMgrBridge() != null) {
            return getVideoViewMgrBridge().getVideoSarDen();
        }
        return 0;
    }
    //</editor-fold>
}
