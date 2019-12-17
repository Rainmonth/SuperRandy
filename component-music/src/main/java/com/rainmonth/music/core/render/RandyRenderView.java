package com.rainmonth.music.core.render;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.rainmonth.music.BuildConfig;
import com.rainmonth.music.core.helper.ConstHelper;
import com.rainmonth.music.core.render.view.IRenderView;
import com.socks.library.KLog;

/**
 * @author 张豪成
 * @date 2019-12-17 11:30
 */
public class RandyRenderView {
    public static final String TAG = RandyRenderView.class.getSimpleName();

    private IRenderView mRenderViewContainer;

    public void requestLayout() {
        if (mRenderViewContainer != null) {

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
                KLog.e(TAG, "parent should be FrameLayout or RelativeLayout");
            }
        }
    }

    public static int getRenderLayoutParams() {
        boolean showTypeChanged = ConstHelper.getShowType() != ConstHelper.SCREEN_TYPE_DEFAULT;
        return showTypeChanged ? ViewGroup.LayoutParams.WRAP_CONTENT : ViewGroup.LayoutParams.MATCH_PARENT;
    }
}
