package com.rainmonth.player.render.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;

import com.rainmonth.common.utils.ToastUtils;
import com.rainmonth.player.listener.GSYVideoShotListener;
import com.rainmonth.player.listener.GSYVideoShotSaveListener;
import com.rainmonth.player.render.GSYRenderView;
import com.rainmonth.player.render.glrender.GSYVideoGLViewBaseRender;
import com.rainmonth.player.render.view.listener.IGSYSurfaceListener;
import com.rainmonth.player.utils.Debugger;
import com.rainmonth.player.utils.FileUtils;
import com.rainmonth.player.utils.GSYVideoType;
import com.rainmonth.player.utils.MeasureHelper;

import java.io.File;

/**
 * 用于显示video的，做了横屏与竖屏的匹配，还有需要rotation需求的
 */
public class GSYTextureView extends TextureView implements TextureView.SurfaceTextureListener, IRenderView, MeasureHelper.MeasureFormVideoParamsListener {

    private IGSYSurfaceListener mIGSYSurfaceListener;

    private MeasureHelper.MeasureFormVideoParamsListener mVideoParamsListener;

    private MeasureHelper measureHelper;

    private SurfaceTexture mSaveTexture;
    private Surface mSurface;

    public GSYTextureView(Context context) {
        super(context);
        init();
    }

    public GSYTextureView(Context context, AttributeSet attrs) {
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
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        if (GSYVideoType.isMediaCodecTexture()) {
            if (mSaveTexture == null) {
                mSaveTexture = surface;
                mSurface = new Surface(surface);
            } else {
                setSurfaceTexture(mSaveTexture);
            }
            if (mIGSYSurfaceListener != null) {
                mIGSYSurfaceListener.onSurfaceAvailable(mSurface);
            }
        } else {
            mSurface = new Surface(surface);
            if (mIGSYSurfaceListener != null) {
                mIGSYSurfaceListener.onSurfaceAvailable(mSurface);
            }
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        if (mIGSYSurfaceListener != null) {
            mIGSYSurfaceListener.onSurfaceSizeChanged(mSurface, width, height);
        }
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {

        //清空释放
        if (mIGSYSurfaceListener != null) {
            mIGSYSurfaceListener.onSurfaceDestroyed(mSurface);
        }
        if (GSYVideoType.isMediaCodecTexture()) {
            return (mSaveTexture == null);
        } else {
            return true;
        }
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        //如果播放的是暂停全屏了
        if (mIGSYSurfaceListener != null) {
            mIGSYSurfaceListener.onSurfaceUpdated(mSurface);
        }
    }

    @Override
    public IGSYSurfaceListener getIGSYSurfaceListener() {
        return mIGSYSurfaceListener;
    }

    @Override
    public void setIGSYSurfaceListener(IGSYSurfaceListener surfaceListener) {
        setSurfaceTextureListener(this);
        mIGSYSurfaceListener = surfaceListener;
    }

    @Override
    public int getSizeH() {
        return getHeight();
    }

    @Override
    public int getSizeW() {
        return getWidth();
    }

    /**
     * 暂停时初始化位图
     */
    @Override
    public Bitmap initCover() {
        Bitmap bitmap = Bitmap.createBitmap(getSizeW(), getSizeH(), Bitmap.Config.RGB_565);
        return getBitmap(bitmap);

    }

    /**
     * 暂停时初始化位图
     */
    @Override
    public Bitmap initCoverHigh() {
        Bitmap bitmap = Bitmap.createBitmap(getSizeW(), getSizeH(), Bitmap.Config.ARGB_8888);
        return getBitmap(bitmap);
    }


    /**
     * 获取截图
     *
     * @param shotHigh 是否需要高清的
     */
    @Override
    public void taskShotPic(GSYVideoShotListener gsyVideoShotListener, boolean shotHigh) {
        if (shotHigh) {
            gsyVideoShotListener.getBitmap(initCoverHigh());
        } else {
            gsyVideoShotListener.getBitmap(initCover());
        }
    }

    /**
     * 保存截图
     *
     * @param high 是否需要高清的
     */
    @Override
    public void saveFrame(final File file, final boolean high, final GSYVideoShotSaveListener gsyVideoShotSaveListener) {
        GSYVideoShotListener gsyVideoShotListener = new GSYVideoShotListener() {
            @Override
            public void getBitmap(Bitmap bitmap) {
                if (bitmap == null) {
                    gsyVideoShotSaveListener.result(false, file);
                } else {
                    FileUtils.saveBitmap(bitmap, file);
                    gsyVideoShotSaveListener.result(true, file);
                }
            }
        };
        if (high) {
            gsyVideoShotListener.getBitmap(initCoverHigh());
        } else {
            gsyVideoShotListener.getBitmap(initCover());
        }

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
        setTransform(transform);
    }

    @Override
    public void setGLRenderer(GSYVideoGLViewBaseRender renderer) {
        Debugger.printfLog(getClass().getSimpleName() + " not support setGLRenderer now");
        ToastUtils.showLong(getClass().getSimpleName() + " not support setGLRenderer now");
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
    public static GSYTextureView addTextureView(Context context, ViewGroup textureViewContainer, int rotate,
                                                final IGSYSurfaceListener gsySurfaceListener,
                                                final MeasureHelper.MeasureFormVideoParamsListener videoParamsListener) {
        if (textureViewContainer.getChildCount() > 0) {
            textureViewContainer.removeAllViews();
        }
        GSYTextureView gsyTextureView = new GSYTextureView(context);
        gsyTextureView.setIGSYSurfaceListener(gsySurfaceListener);
        gsyTextureView.setVideoParamsListener(videoParamsListener);
        gsyTextureView.setRotation(rotate);
        GSYRenderView.addToParent(textureViewContainer, gsyTextureView);
        return gsyTextureView;
    }
}