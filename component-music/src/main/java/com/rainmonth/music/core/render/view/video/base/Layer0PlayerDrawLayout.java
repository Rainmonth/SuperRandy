package com.rainmonth.music.core.render.view.video.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rainmonth.music.core.helper.MeasureHelper;
import com.rainmonth.music.core.render.RenderViewHolder;
import com.rainmonth.music.core.render.effect.NoEffectShader;
import com.rainmonth.music.core.render.glrender.GLSurfaceViewBaseRenderer;
import com.rainmonth.music.core.render.glrender.IShader;
import com.rainmonth.music.core.render.view.RandyGLSurfaceView;
import com.rainmonth.music.core.render.view.listener.SurfaceListener;

/**
 * 该层主要负责绘制
 *
 * @author 张豪成
 * @date 2019-12-17 11:52
 */
public abstract class Layer0PlayerDrawLayout extends FrameLayout implements SurfaceListener,
        MeasureHelper.MeasureFormVideoParamsListener {

    protected Surface mSurface;             //
    protected RenderViewHolder mRenderView;
    protected ViewGroup mRenderViewParent;
    protected Bitmap mFullPauseBitmap;
    protected int mRotate;

    // for GLSurfaceView

    //GL的滤镜
    protected IShader mEffectFilter = new NoEffectShader();
    //GL的自定义渲染
    protected GLSurfaceViewBaseRenderer mRenderer;
    //GL的角度
    protected float[] mMatrixGL = null;
    //GL的布局模式
    protected int mMode = RandyGLSurfaceView.MEASURE_MODE_LAYOUT_SIZE;


    public Layer0PlayerDrawLayout(Context context) {
        super(context);
    }

    public Layer0PlayerDrawLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Layer0PlayerDrawLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //<editor-fold>SurfaceListener实现

    @Override
    public void onSurfaceCreated(Surface surface) {

    }

    @Override
    public void onSurfaceChanged(Surface surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceDestroyed(Surface surface) {
        setDisplay(null);
        releaseSurface(surface);
        return true;
    }

    @Override
    public void onSurfaceUpdated(Surface surface) {

    }
    //</editor-fold>

    protected RenderViewHolder getRenderProxy() {
        return mRenderView;
    }

    protected void initCover() {
        if (mRenderView != null) {
            mFullPauseBitmap = mRenderView.initCover();
        }
    }


    protected void setSmallVideoTextureView() {

    }

    /**
     * 添加RenderView
     */
    protected void addRenderView() {
        mRenderView = new RenderViewHolder();
        mRenderView.initRenderView(getContext(), mRenderViewParent, mRotate, this, this);
    }

    //<editor-fold>待实现的方法
    protected abstract void showPauseCover();

    protected abstract void releasePauseCover();

    protected abstract void setDisplay(Surface surface);

    protected abstract void releaseSurface(Surface surface);
    //</editor-fold>
}
