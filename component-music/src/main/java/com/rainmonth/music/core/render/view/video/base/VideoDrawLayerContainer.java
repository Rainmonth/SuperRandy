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
import com.rainmonth.music.core.render.RandyRenderView;
import com.rainmonth.music.core.render.view.listener.SurfaceListener;

/**
 * 该层主要负责绘制
 *
 * @author 张豪成
 * @date 2019-12-17 11:52
 */
public abstract class VideoDrawLayerContainer extends FrameLayout implements SurfaceListener,
        MeasureHelper.MeasureFormVideoParamsListener {

    protected Surface mSurface;             //
    protected RandyRenderView mRenderView;
    protected ViewGroup mRenderViewParent;
    protected Bitmap mFullPauseBitmap;
    protected int mRotate;

    // for GLSurfaceView

//    //GL的滤镜
//    protected GSYVideoGLView.ShaderInterface mEffectFilter = new NoEffect();
//    //GL的自定义渲染
//    protected GSYVideoGLViewBaseRender mRenderer;
//    //GL的角度
//    protected float[] mMatrixGL = null;
//    //GL的布局模式
//    protected int mMode = GSYVideoGLView.MODE_LAYOUT_SIZE;


    public VideoDrawLayerContainer(Context context) {
        super(context);
    }

    public VideoDrawLayerContainer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public VideoDrawLayerContainer(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
    public void onSurfaceDestroyed(Surface surface) {

    }

    @Override
    public void onSurfaceUpdated(Surface surface) {

    }
    //</editor-fold>

    protected RandyRenderView getRenderProxy() {
        return mRenderView;
    }

    //<editor-fold>待实现的方法
    protected abstract void showPauseCover();

    protected abstract void releasePauseCover();

    protected abstract void setSmallVideoTextureView();

    protected abstract void setDisplay(Surface surface);

    protected abstract void releaseSurface(Surface surface);
    //</editor-fold>
}
