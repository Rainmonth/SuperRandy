package com.rainmonth.music.core.render.view;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.view.View;

import com.rainmonth.music.core.helper.MeasureHelper;
import com.rainmonth.music.core.render.view.listener.SurfaceListener;

/**
 * 渲染要用到的通用接口定义
 *
 * @author 张豪成
 * @date 2019-12-17 11:30
 */
public interface IRenderView {
    SurfaceListener getRandySurfaceListener();

    /**
     * Surface变化监听
     *
     * @param listener RandySurfaceListener实例
     */
    void setRandySurfaceListener(SurfaceListener listener);

    /**
     * 获取当前View的宽度
     *
     * @return view的宽度
     */
    int getSizeW();

    /**
     * 获取当前View的高度
     *
     * @return view的高度
     */
    int getSizeH();

    /**
     * 获取真正用来渲染视频的View
     *
     * @return 返回实现了IRenderView的实例
     */
    View getRealRenderView();

    /**
     * 渲染view通过MeasureFormVideoParamsListener获取视频的相关参数，必须
     */
    void setVideoParamsListener(MeasureHelper.MeasureFormVideoParamsListener listener);

//    /**
//     * 截图
//     */
//    todo
//    void taskShotPic(GSYVideoShotListener gsyVideoShotListener, boolean shotHigh);

//    /**
//     * 保存当前帧
//     */
//    todo
//    void saveFrame(final File file, final boolean high, final GSYVideoShotSaveListener gsyVideoShotSaveListener);

    /**
     * 获取当前画面的bitmap，没有返回空
     */
    Bitmap initCover();

    /**
     * 获取当前画面的高质量bitmap，没有返回空
     */
    Bitmap initCoverHigh();

    void onRenderResume();

    void onRenderPause();

    void releaseRenderAll();

    void setRenderMode(int mode);

    void setRenderTransform(Matrix transform);

//    void setGLRenderer(GSYVideoGLViewBaseRender renderer);

    void setGLMVPMatrix(float[] MVPMatrix);

//    void setGLEffectFilter(GSYVideoGLView.ShaderInterface effectFilter);

}
