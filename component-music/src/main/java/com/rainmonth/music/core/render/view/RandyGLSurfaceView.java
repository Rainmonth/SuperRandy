package com.rainmonth.music.core.render.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.View;

import com.rainmonth.common.utils.FileUtils;
import com.rainmonth.music.core.helper.MeasureHelper;
import com.rainmonth.music.core.render.effect.NoEffectShader;
import com.rainmonth.music.core.render.glrender.GLSurfaceViewBaseRenderer;
import com.rainmonth.music.core.render.glrender.IShader;
import com.rainmonth.music.core.render.glrender.SimpleGLSurfaceViewRenderer;
import com.rainmonth.music.core.render.view.listener.GLSurfaceListener;
import com.rainmonth.music.core.render.view.listener.ShotSaveCallback;
import com.rainmonth.music.core.render.view.listener.SurfaceListener;
import com.rainmonth.music.core.render.view.listener.VideoShotListener;
import com.socks.library.KLog;

import java.io.File;

/**
 * 支持GL效果的VideoView
 *
 * @author 张豪成
 * @date 2019-12-17 11:35
 */
public class RandyGLSurfaceView extends GLSurfaceView implements IRenderView, GLSurfaceListener,
        MeasureHelper.MeasureFormVideoParamsListener {
    public static final String TAG = RandyGLSurfaceView.class.getSimpleName();
    public static final int MEASURE_MODE_LAYOUT_SIZE = 0;   // 基于布局参数测量
    public static final int MEASURE_MODE_RENDER_SIZE = 1;   // 计入渲染参数测量

    private int mMeasureMode = MEASURE_MODE_LAYOUT_SIZE;    // 测量模式
    private GLSurfaceViewBaseRenderer mRenderer;            // 渲染器
    private IShader mShaderEffect = new NoEffectShader();   // GL渲染shader

    private Context mContext;                               // 上下文
    private SurfaceListener mSurfaceListener;               // 通用监听器
    private GLSurfaceListener mGLSurfaceListener;           // GLSurfaceView 特有的监听器

    private MeasureHelper mMeasureHelper;                   // 测量格局类
    private MeasureHelper.MeasureFormVideoParamsListener mVideoParamsListener;

    private float[] mMvpMatrix;

    public RandyGLSurfaceView(Context context) {
        super(context);
        init(context);
    }

    public RandyGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        setEGLContextClientVersion(2);
        mRenderer = new SimpleGLSurfaceViewRenderer();
        mMeasureHelper = new MeasureHelper(this, this);
        mRenderer.setSurfaceView(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mRenderer != null) {
            mRenderer.initRenderSize();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mMeasureMode == MEASURE_MODE_RENDER_SIZE) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            mMeasureHelper.prepareMeasure(widthMeasureSpec, heightMeasureSpec, (int) getRotation());
            initRenderMeasure();
        } else {
            mMeasureHelper.prepareMeasure(widthMeasureSpec, heightMeasureSpec, (int) getRotation());
            setMeasuredDimension(mMeasureHelper.getMeasuredWidth(), mMeasureHelper.getMeasuredHeight());
        }
    }

    protected void initRenderMeasure() {
        if (mVideoParamsListener != null && mMeasureMode == MEASURE_MODE_RENDER_SIZE) {
            try {
                int videoWidth = mVideoParamsListener.getCurrentVideoWidth();
                int videoHeight = mVideoParamsListener.getCurrentVideoHeight();
                if (this.mRenderer != null) {
                    this.mRenderer.setCurrentViewWidth(mMeasureHelper.getMeasuredWidth());
                    this.mRenderer.setCurrentViewHeight(mMeasureHelper.getMeasuredHeight());
                    this.mRenderer.setCurrentVideoWidth(videoWidth);
                    this.mRenderer.setCurrentVideoHeight(videoHeight);
                }
            } catch (Exception e) {
                KLog.e(TAG, e);
            }
        }
    }

    public void initRender() {
        setRenderer(mRenderer);
    }

    public void setGlSurfaceListener(GLSurfaceListener glSurfaceListener) {
        mGLSurfaceListener = glSurfaceListener;
        mRenderer.setGLSurfaceListener(mGLSurfaceListener);
    }

    public void setCustomRenderer(GLSurfaceViewBaseRenderer renderer) {
        this.mRenderer = renderer;
        mRenderer.setSurfaceView(this);
        initRenderMeasure();
    }

    public GLSurfaceViewBaseRenderer getRenderer() {
        return mRenderer;
    }

    public IShader getShaderEffect() {
        return mShaderEffect;
    }

    public void setShaderEffect(IShader shaderEffect) {
        if (shaderEffect != null) {
            mShaderEffect = shaderEffect;
            mRenderer.setShaderEffect(mShaderEffect);
        }
    }

    public float[] getMvpMatrix() {
        return mMvpMatrix;
    }

    public void setMvpMatrix(float[] matrix) {
        if (matrix != null) {
            mMvpMatrix = matrix;
            mRenderer.setMvpMatrix(mMvpMatrix);
        }
    }

    public void takeShotPic() {
        if (mRenderer != null) {
            mRenderer.takeShotPic();
        }
    }

    public int getMeasureMode() {
        return mMeasureMode;
    }

    /**
     * 测量模式
     *
     * @param measureMode {@link RandyGLSurfaceView#MEASURE_MODE_LAYOUT_SIZE}、
     *                    {@link RandyGLSurfaceView#MEASURE_MODE_RENDER_SIZE}
     */
    public void setMeasureMode(int measureMode) {
        this.mMeasureMode = measureMode;
    }

    public void releaseAll() {
        if (mRenderer != null) {
            mRenderer.releaseAll();
        }
    }

    public void setVideoShotListener(VideoShotListener listener, boolean showHigh) {
        if (mRenderer != null) {
            this.mRenderer.setVideoShotListener(listener, showHigh);
        }
    }

    //<editor-fold>IRenderView实现
    @Override
    public SurfaceListener getRandySurfaceListener() {
        return mSurfaceListener;
    }

    @Override
    public void setRandySurfaceListener(SurfaceListener listener) {
        setGlSurfaceListener(this);
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
        mVideoParamsListener = listener;
    }

    @Override
    public void taskShotPic(VideoShotListener videoShotListener, boolean shotHigh) {
        if (videoShotListener != null) {
            setVideoShotListener(videoShotListener, shotHigh);
            takeShotPic();
        }
    }

    @Override
    public void saveFrame(File file, boolean high, ShotSaveCallback callback) {
        VideoShotListener videoShotListener = new VideoShotListener() {
            @Override
            public void getBitmap(Bitmap bitmap) {
                if (callback != null) {
                    if (bitmap == null) {
                        callback.onShotSaveResult(false, file);
                    } else {
                        FileUtils.saveBitmap(bitmap, file);
                        callback.onShotSaveResult(true, file);
                    }
                }
            }
        };
        setVideoShotListener(videoShotListener, high);
        takeShotPic();
    }

    @Override
    public Bitmap initCover() {
        KLog.w(TAG, TAG + " not support initCover now!");
        return null;
    }

    @Override
    public Bitmap initCoverHigh() {
        KLog.w(TAG, TAG + " not support initCoverHigh now!");
        return null;
    }

    @Override
    public void onRenderResume() {
        requestLayout();
        onResume();
    }

    @Override
    public void onRenderPause() {
        requestLayout();
        onPause();
    }

    @Override
    public void releaseRenderAll() {
        requestLayout();
        releaseAll();
    }

    @Override
    public void setRenderTransform(Matrix transform) {
        KLog.w(TAG, TAG + " not support setRenderTransform now");
    }

    @Override
    public void setGLRenderer(GLSurfaceViewBaseRenderer renderer) {
        setCustomRenderer(renderer);
    }

    @Override
    public void setGLMVPMatrix(float[] mvpMatrix) {
        setMvpMatrix(mvpMatrix);
    }

    @Override
    public void setGLEffectFilter(IShader shader) {
        setShaderEffect(shader);
    }

    //</editor-fold>

    //<editor-fold>GLSurfaceListener实现

    @Override
    public void onSurfaceCreated(Surface surface) {
        if (mSurfaceListener != null) {
            mSurfaceListener.onSurfaceCreated(surface);
        }
    }

    //</editor-fold>
    //<editor-fold> MeasureFormVideoParamsListener实现

    @Override
    public int getCurrentVideoWidth() {
        if (mVideoParamsListener != null) {
            mVideoParamsListener.getCurrentVideoWidth();
        }
        return 0;
    }

    @Override
    public int getCurrentVideoHeight() {
        if (mVideoParamsListener != null) {
            mVideoParamsListener.getCurrentVideoHeight();
        }
        return 0;
    }

    @Override
    public int getVideoSarNum() {
        if (mVideoParamsListener != null) {
            mVideoParamsListener.getVideoSarNum();
        }
        return 0;
    }

    @Override
    public int getVideoSarDen() {
        if (mVideoParamsListener != null) {
            mVideoParamsListener.getVideoSarDen();
        }
        return 0;
    }

    //</editor-fold>
}
