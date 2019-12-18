package com.rainmonth.music.core.render.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;

import com.rainmonth.music.core.helper.ConstHelper;
import com.rainmonth.music.core.helper.MeasureHelper;
import com.rainmonth.music.core.render.RenderViewHolder;
import com.rainmonth.music.core.render.view.listener.SurfaceListener;
import com.socks.library.KLog;

/**
 * @author 张豪成
 * @date 2019-12-17 11:34
 */
public class RandyTextureView extends TextureView implements IRenderView,
        TextureView.SurfaceTextureListener,
        MeasureHelper.MeasureFormVideoParamsListener {

    private static final String TAG = RandyTextureView.class.getSimpleName();
    private SurfaceListener mSurfaceListener;
    private MeasureHelper mMeasureHelper;
    private MeasureHelper.MeasureFormVideoParamsListener mVideoParamsListener;
    private SurfaceTexture mSurfaceTexture;
    private Surface mSurface;

    public RandyTextureView(Context context) {
        super(context);
        init();
    }

    public RandyTextureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mMeasureHelper = new MeasureHelper(this, this);
    }

    public static RandyTextureView addRenderView(Context context, ViewGroup renderViewContainer, int rotate,
                                          final SurfaceListener surfaceListener,
                                          final MeasureHelper.MeasureFormVideoParamsListener paramsListener) {
        if (renderViewContainer == null) {
            KLog.e(TAG, "renderViewContainer is null");
            return null;
        }
        if (renderViewContainer.getChildCount() > 0) {
            renderViewContainer.removeAllViews();
        }
        RandyTextureView randyTextureView = new RandyTextureView(context);
        randyTextureView.setRandySurfaceListener(surfaceListener);
        randyTextureView.setVideoParamsListener(paramsListener);
        randyTextureView.setRotation(rotate);
        RenderViewHolder.addToParent(renderViewContainer, randyTextureView);
        return randyTextureView;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mMeasureHelper.doMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mMeasureHelper.getMeasuredWidth(), mMeasureHelper.getMeasuredHeight());
    }

    //<editor-fold> IRenderView实现
    @Override
    public SurfaceListener getRandySurfaceListener() {
        return mSurfaceListener;
    }

    @Override
    public void setRandySurfaceListener(SurfaceListener listener) {
        this.mSurfaceListener = listener;
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
        Bitmap bitmap = Bitmap.createBitmap(
                getSizeW(), getSizeH(), Bitmap.Config.RGB_565);
        return getBitmap(bitmap);
    }

    @Override
    public Bitmap initCoverHigh() {
        Bitmap bitmap = Bitmap.createBitmap(
                getSizeW(), getSizeH(), Bitmap.Config.ARGB_8888);
        return getBitmap(bitmap);
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

    //<editor-fold>

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        if (ConstHelper.isMediaCodecTexture()) {
            if (mSurfaceTexture == null) {
                mSurfaceTexture = surface;
                mSurface = new Surface(surface);
            } else {
                setSurfaceTexture(mSurfaceTexture);
            }
            if (mSurfaceListener != null) {
                mSurfaceListener.onSurfaceCreated(mSurface);
            }
        } else {
            mSurface = new Surface(surface);
            if (mSurfaceListener != null) {
                mSurfaceListener.onSurfaceCreated(mSurface);
            }
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        if (mSurfaceListener != null) {
            mSurfaceListener.onSurfaceChanged(mSurface, width, height);
        }
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        if (mSurfaceListener != null) {
            mSurfaceListener.onSurfaceDestroyed(mSurface);
        }
        if (ConstHelper.isMediaCodecTexture()) {
            // 返回true表示该方法调用后SurfaceTexture不会再进行任何渲染操作
            // 返回false表示我们需要手动SurfaceTexture.release()方法
            return mSurfaceTexture == null;
        } else {
            return true;
        }
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        if (mSurfaceListener != null) {
            mSurfaceListener.onSurfaceUpdated(mSurface);
        }
    }

    //</editor-fold>

    //<editor-fold>

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
    //</editor-fold
}
