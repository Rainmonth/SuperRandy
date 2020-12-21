package com.rainmonth.player.render.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import com.rainmonth.common.utils.ToastUtils;
import com.rainmonth.player.listener.GSYVideoShotListener;
import com.rainmonth.player.listener.GSYVideoShotSaveListener;
import com.rainmonth.player.render.GSYRenderView;
import com.rainmonth.player.render.glrender.GSYVideoGLViewBaseRender;
import com.rainmonth.player.render.view.listener.IGSYSurfaceListener;
import com.rainmonth.player.utils.Debugger;
import com.rainmonth.player.utils.MeasureHelper;

import java.io.File;

/**
 * SurfaceView
 */
public class GSYSurfaceView extends SurfaceView implements SurfaceHolder.Callback2, IRenderView, MeasureHelper.MeasureFormVideoParamsListener {

    private IGSYSurfaceListener mIGSYSurfaceListener;

    private MeasureHelper.MeasureFormVideoParamsListener mVideoParamsListener;

    private MeasureHelper measureHelper;

    public GSYSurfaceView(Context context) {
        super(context);
        init();
    }

    public GSYSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        measureHelper = new MeasureHelper(this, this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureHelper.prepareMeasure(widthMeasureSpec, heightMeasureSpec, (int) getRotation());
        setMeasuredDimension(measureHelper.getMeasuredWidth(), measureHelper.getMeasuredHeight());
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (mIGSYSurfaceListener != null) {
            mIGSYSurfaceListener.onSurfaceAvailable(holder.getSurface());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (mIGSYSurfaceListener != null) {
            mIGSYSurfaceListener.onSurfaceSizeChanged(holder.getSurface(), width, height);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //清空释放
        if (mIGSYSurfaceListener != null) {
            mIGSYSurfaceListener.onSurfaceDestroyed(holder.getSurface());
        }
    }

    @Override
    public void surfaceRedrawNeeded(SurfaceHolder holder) {
    }

    @Override
    public IGSYSurfaceListener getIGSYSurfaceListener() {
        return mIGSYSurfaceListener;
    }

    @Override
    public void setIGSYSurfaceListener(IGSYSurfaceListener surfaceListener) {
        getHolder().addCallback(this);
        this.mIGSYSurfaceListener = surfaceListener;
    }

    @Override
    public int getSizeH() {
        return getHeight();
    }

    @Override
    public int getSizeW() {
        return getWidth();
    }

    @Override
    public Bitmap initCover() {
        Debugger.printfLog(getClass().getSimpleName() + " not support initCover now");
        return null;
    }

    @Override
    public Bitmap initCoverHigh() {
        Debugger.printfLog(getClass().getSimpleName() + " not support initCoverHigh now");
        return null;
    }

    /**
     * 获取截图
     *
     * @param shotHigh 是否需要高清的
     */
    public void taskShotPic(GSYVideoShotListener gsyVideoShotListener, boolean shotHigh) {
        Debugger.printfLog(getClass().getSimpleName() + " not support taskShotPic now");
        ToastUtils.showLong(getClass().getSimpleName() + " not support taskShotPic now");
    }

    /**
     * 保存截图
     *
     * @param high 是否需要高清的
     */
    public void saveFrame(final File file, final boolean high, final GSYVideoShotSaveListener gsyVideoShotSaveListener) {
        Debugger.printfLog(getClass().getSimpleName() + " not support saveFrame now");
        ToastUtils.showLong(getClass().getSimpleName() + " not support saveFrame now");
    }

    @Override
    public View getRenderView() {
        return this;
    }

    @Override
    public void onRenderResume() {
        Debugger.printfLog(getClass().getSimpleName() + " not support onRenderResume now");
        ToastUtils.showLong(getClass().getSimpleName() + " not support onRenderResume now");
    }

    @Override
    public void onRenderPause() {
        Debugger.printfLog(getClass().getSimpleName() + " not support onRenderPause now");
        ToastUtils.showLong(getClass().getSimpleName() + " not support onRenderPause now");
    }

    @Override
    public void releaseRenderAll() {
        Debugger.printfLog(getClass().getSimpleName() + " not support releaseRenderAll now");
        ToastUtils.showLong(getClass().getSimpleName() + " not support releaseRenderAll now");
    }

    @Override
    public void setRenderMode(int mode) {
        Debugger.printfLog(getClass().getSimpleName() + " not support setRenderMode now");
        ToastUtils.showLong(getClass().getSimpleName() + " not support setRenderMode now");
    }


    @Override
    public void setRenderTransform(Matrix transform) {
        Debugger.printfLog(getClass().getSimpleName() + " not support setRenderTransform now");
        ToastUtils.showLong(this.getClass().getSimpleName() + " not support setRenderTransform now");
    }

    @Override
    public void setGLRenderer(GSYVideoGLViewBaseRender renderer) {
        Debugger.printfLog(getClass().getSimpleName() + " not support setGLRenderer now");
        ToastUtils.showLong(this.getClass().getSimpleName() + " not support setGLRenderer now");
    }

    @Override
    public void setGLMVPMatrix(float[] MVPMatrix) {
        Debugger.printfLog(getClass().getSimpleName() + " not support setGLMVPMatrix now");
        ToastUtils.showLong(getClass().getSimpleName() + " not support setGLMVPMatrix now");
    }

    /**
     * 设置滤镜效果
     */
    @Override
    public void setGLEffectFilter(GSYVideoGLView.ShaderInterface effectFilter) {
        Debugger.printfLog(getClass().getSimpleName() + " not support setGLEffectFilter now");
        ToastUtils.showLong(getClass().getSimpleName() + " not support setGLEffectFilter now");
    }


    @Override
    public void setVideoParamsListener(MeasureHelper.MeasureFormVideoParamsListener listener) {
        mVideoParamsListener = listener;
    }

    @Override
    public int getCurrentVideoWidth() {
        if (mVideoParamsListener != null) {
            return mVideoParamsListener.getCurrentVideoWidth();
        }
        return 0;
    }

    @Override
    public int getCurrentVideoHeight() {
        if (mVideoParamsListener != null) {
            return mVideoParamsListener.getCurrentVideoHeight();
        }
        return 0;
    }

    @Override
    public int getVideoSarNum() {
        if (mVideoParamsListener != null) {
            return mVideoParamsListener.getVideoSarNum();
        }
        return 0;
    }

    @Override
    public int getVideoSarDen() {
        if (mVideoParamsListener != null) {
            return mVideoParamsListener.getVideoSarDen();
        }
        return 0;
    }

    /**
     * 添加播放的view
     */
    public static GSYSurfaceView addSurfaceView(Context context, ViewGroup textureViewContainer, int rotate,
                                                final IGSYSurfaceListener gsySurfaceListener,
                                                final MeasureHelper.MeasureFormVideoParamsListener videoParamsListener) {
        if (textureViewContainer.getChildCount() > 0) {
            textureViewContainer.removeAllViews();
        }
        GSYSurfaceView showSurfaceView = new GSYSurfaceView(context);
        showSurfaceView.setIGSYSurfaceListener(gsySurfaceListener);
        showSurfaceView.setVideoParamsListener(videoParamsListener);
        showSurfaceView.setRotation(rotate);
        GSYRenderView.addToParent(textureViewContainer, showSurfaceView);
        return showSurfaceView;
    }

}
