package com.rainmonth.music.core.render.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.View;

import com.rainmonth.music.core.helper.MeasureHelper;
import com.rainmonth.music.core.render.view.listener.GLSurfaceListener;
import com.rainmonth.music.core.render.view.listener.SurfaceListener;

/**
 * 支持GL效果的VideoView
 * todo 后面专门处openGL时再完善
 * @author 张豪成
 * @date 2019-12-17 11:35
 */
public class RandyGLSurfaceView extends GLSurfaceView implements IRenderView, GLSurfaceListener,
        MeasureHelper.MeasureFormVideoParamsListener {
    private Context mContext;
    private SurfaceListener mSurfaceListener;
    private MeasureHelper mMeasureHelper;
    private MeasureHelper.MeasureFormVideoParamsListener mVideoParamsListener;

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
        mMeasureHelper = new MeasureHelper(this, this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    //<editor-fold>IRenderView实现
    @Override
    public SurfaceListener getRandySurfaceListener() {
        return null;
    }

    @Override
    public void setRandySurfaceListener(SurfaceListener listener) {

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
        return null;
    }

    @Override
    public void setVideoParamsListener(MeasureHelper.MeasureFormVideoParamsListener listener) {

    }

    @Override
    public Bitmap initCover() {
        return null;
    }

    @Override
    public Bitmap initCoverHigh() {
        return null;
    }

    @Override
    public void onRenderResume() {

    }

    @Override
    public void onRenderPause() {

    }

    @Override
    public void releaseRenderAll() {

    }

    @Override
    public void setRenderTransform(Matrix transform) {

    }

    @Override
    public void setGLMVPMatrix(float[] MVPMatrix) {

    }
    //</editor-fold>

    //<editor-fold>GLSurfaceListener实现

    @Override
    public void onSurfaceCreated(Surface surface) {

    }

    //</editor-fold>
    //<editor-fold> MeasureFormVideoParamsListener实现

    @Override
    public int getCurrentVideoWidth() {
        return 0;
    }

    @Override
    public int getCurrentVideoHeight() {
        return 0;
    }

    @Override
    public int getVideoSarNum() {
        return 0;
    }

    @Override
    public int getVideoSarDen() {
        return 0;
    }

    //</editor-fold>
}
