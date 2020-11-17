package com.rainmonth.music.core.render;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.rainmonth.common.utils.log.LogUtils;
import com.rainmonth.music.BuildConfig;
import com.rainmonth.music.core.helper.ConstHelper;
import com.rainmonth.music.core.helper.MeasureHelper;
import com.rainmonth.music.core.render.view.IRenderView;
import com.rainmonth.music.core.render.view.RandySurfaceView;
import com.rainmonth.music.core.render.view.RandyTextureView;
import com.rainmonth.music.core.render.view.listener.SurfaceListener;

/**
 * RenderView持有者
 *
 * @author 张豪成
 * @date 2019-12-17 11:30
 */
public class RenderViewHolder {
    public static final String TAG = RenderViewHolder.class.getSimpleName();

    private IRenderView mRenderView;

    /**
     * 初始化mRenderView，并将其添加到期父容器中
     *
     * @param context             ctx
     * @param renderViewParent    父容器
     * @param rotate              旋转角度
     * @param surfaceListener     Surface变化监听{@link SurfaceListener}
     * @param videoParamsListener VideoParams变化监听{@link com.rainmonth.music.core.helper.MeasureHelper.MeasureFormVideoParamsListener}
     */
    public void initRenderView(Context context, ViewGroup renderViewParent, int rotate,
                               SurfaceListener surfaceListener,
                               MeasureHelper.MeasureFormVideoParamsListener videoParamsListener) {
        if (ConstHelper.getRenderType() == ConstHelper.RENDER_TYPE_SURFACE) {
            mRenderView = RandySurfaceView.addRenderView(context, renderViewParent, rotate, surfaceListener, videoParamsListener);
        } else if (ConstHelper.getRenderType() == ConstHelper.RENDER_TYPE_GL_SURFACE) {
            // todo
            mRenderView = null;
        } else {
            mRenderView = RandyTextureView.addRenderView(context, renderViewParent, rotate, surfaceListener, videoParamsListener);
        }
    }

    public void requestLayout() {
        if (mRenderView != null) {
            mRenderView.getRealRenderView().requestLayout();
        }
    }

    public float getRotation() {
        if (mRenderView != null) {
            return mRenderView.getRealRenderView().getRotation();
        }
        return 0f;
    }

    public void setRotation(float rotation) {
        if (mRenderView != null) {
            mRenderView.getRealRenderView().setRotation(rotation);
        }
    }

    public void invalidate() {
        if (mRenderView != null) {
            mRenderView.getRealRenderView().invalidate();
        }
    }

    public int getWidth() {
        return mRenderView != null ? mRenderView.getRealRenderView().getWidth() : 0;
    }

    public int getHeight() {
        return mRenderView != null ? mRenderView.getRealRenderView().getHeight() : 0;
    }

    public View getRealRenderView() {
        return mRenderView != null ? mRenderView.getRealRenderView() : null;
    }

    public ViewGroup.LayoutParams getLayoutParams() {
        return mRenderView.getRealRenderView().getLayoutParams();
    }

    public void setLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (mRenderView != null) {
            mRenderView.getRealRenderView().setLayoutParams(layoutParams);
        }
    }

    /**
     * 暂停时初始化位图
     */
    public Bitmap initCover() {
        if (mRenderView != null)
            return mRenderView.initCover();
        return null;
    }

    /**
     * 暂停时初始化位图
     */
    public Bitmap initCoverHigh() {
        if (mRenderView != null)
            return mRenderView.initCoverHigh();
        return null;
    }

    public void onResume() {
        if (mRenderView != null) {
            mRenderView.onRenderResume();
        }
    }

    public void onPause() {
        if (mRenderView != null) {
            mRenderView.onRenderPause();
        }
    }

    /**
     * 将RenderView添加到父容器中
     *
     * @param parent     父容器
     * @param renderView 渲染视图 {@link IRenderView}
     */
    public static void addToParent(ViewGroup parent, View renderView) {
        int params = getRenderLayoutParams();
        if (parent instanceof FrameLayout) {
            FrameLayout.LayoutParams p = new FrameLayout.LayoutParams(params, params);
            p.gravity = Gravity.CENTER;
            parent.addView(renderView, p);
        } else if (parent instanceof RelativeLayout) {
            RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(params, params);
            p.addRule(RelativeLayout.CENTER_IN_PARENT);
            parent.addView(renderView, p);
        } else {
            if (BuildConfig.DEBUG) {
                throw new IllegalArgumentException("parent should be FrameLayout or RelativeLayout");
            } else {
                LogUtils.e(TAG, "parent should be FrameLayout or RelativeLayout");
            }
        }
    }

    public static int getRenderLayoutParams() {
        boolean showTypeChanged = ConstHelper.getShowType() != ConstHelper.SCREEN_TYPE_DEFAULT;
        return showTypeChanged ? ViewGroup.LayoutParams.WRAP_CONTENT : ViewGroup.LayoutParams.MATCH_PARENT;
    }
}
