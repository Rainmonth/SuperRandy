package com.rainmonth.music.core.render.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import com.rainmonth.music.core.helper.MeasureHelper;
import com.rainmonth.music.core.render.RandyRenderView;
import com.rainmonth.music.core.render.view.listener.SurfaceListener;
import com.socks.library.KLog;

/**
 * @author 张豪成
 * @date 2019-12-17 11:31
 */
public class RandySurfaceView extends SurfaceView implements SurfaceHolder.Callback2, IRenderView,
        MeasureHelper.MeasureFormVideoParamsListener {

    private final String TAG = RandySurfaceView.class.getSimpleName();
    private SurfaceListener mSurfaceListener;
    private MeasureHelper mMeasureHelper;
    private MeasureHelper.MeasureFormVideoParamsListener mVideoParamsListener;

    public RandySurfaceView(Context context) {
        super(context);
        init();
    }

    public RandySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mMeasureHelper = new MeasureHelper(this, this);
    }

    public RandySurfaceView addRenderView(Context context, ViewGroup renderViewContainer, int rotate,
                                          final SurfaceListener surfaceListener,
                                          final MeasureHelper.MeasureFormVideoParamsListener paramsListener) {
        if (renderViewContainer == null) {
            KLog.e(TAG, "renderViewContainer is null");
            return null;
        }
        if (renderViewContainer.getChildCount() > 0) {
            renderViewContainer.removeAllViews();
        }
        RandySurfaceView surfaceView = new RandySurfaceView(context);
        surfaceView.setRandySurfaceListener(surfaceListener);
        surfaceView.setVideoParamsListener(paramsListener);
        surfaceView.setRotation(rotate);
        RandyRenderView.addToParent(renderViewContainer, surfaceView);
        return surfaceView;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mMeasureHelper.doMeasure(widthMeasureSpec, heightMeasureSpec);
        // 该方法一定要调用，调用才能保存测量的宽高
        setMeasuredDimension(mMeasureHelper.getMeasuredWidth(), mMeasureHelper.getMeasuredHeight());
    }

    //<editor-fold> SurfaceHolder.Callback2实现

    @Override
    public void surfaceRedrawNeeded(SurfaceHolder holder) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (mSurfaceListener != null) {
            mSurfaceListener.onSurfaceCreated(holder.getSurface());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (mSurfaceListener != null) {
            mSurfaceListener.onSurfaceChanged(holder.getSurface(), width, height);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mSurfaceListener != null) {
            mSurfaceListener.onSurfaceDestroyed(holder.getSurface());
        }
    }


    //</editor-fold>

    //<editor-fold> IRenderView实现
    @Override
    public SurfaceListener getRandySurfaceListener() {
        return mSurfaceListener;
    }

    @Override
    public void setRandySurfaceListener(SurfaceListener listener) {
        getHolder().addCallback(this);
        mSurfaceListener = listener;
    }

    @Override
    public int getSizeW() {
        return getWidth();
    }

    @Override
    public int getSizeH() {
        return getHeight();
    }

    @Override
    public View getRealRenderView() {
        return this;
    }

    @Override
    public void setVideoParamsListener(MeasureHelper.MeasureFormVideoParamsListener listener) {
        this.mVideoParamsListener = listener;
    }

    @Override
    public Bitmap initCover() {
        KLog.d(TAG, TAG + " not support initCover now!");
        return null;
    }

    @Override
    public Bitmap initCoverHigh() {
        KLog.d(TAG, TAG + " not support initCoverHigh now!");
        return null;
    }

    @Override
    public void onRenderResume() {
        KLog.d(TAG, TAG + " not support onRenderResume now!");
    }

    @Override
    public void onRenderPause() {
        KLog.d(TAG, TAG + " not support onRenderPause now!");
    }

    @Override
    public void releaseRenderAll() {
        KLog.d(TAG, TAG + " not support releaseRenderAll now!");
    }

    @Override
    public void setRenderMode(int mode) {
        KLog.d(TAG, TAG + " not support setRenderMode now!");
    }

    @Override
    public void setRenderTransform(Matrix transform) {
        KLog.d(TAG, TAG + " not support setRenderTransform now!");
    }

    @Override
    public void setGLMVPMatrix(float[] MVPMatrix) {
        KLog.d(TAG, TAG + " not support setGLMVPMatrix now!");
    }
    //</editor-fold>


    //<editor-fold> MeasureHelper.MeasureFormVideoParamsListener实现

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

    //</editor-fold>
}
