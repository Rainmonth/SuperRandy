package com.rainmonth.image.mvp.ui.common;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;

import com.rainmonth.utils.DensityUtils;

public class RoundRectsActivity extends Activity {
    private int leftPadding, rightPadding;
    private int rightRadius;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new RoundRectView(this));
        leftPadding = DensityUtils.dip2px(this, 10);
        rightPadding = DensityUtils.dip2px(this, 10);
        rightRadius = DensityUtils.dip2px(this, 75);
    }

    private static class RoundRectView extends View {
        // Rect类主要用于表示坐标系中的一块矩形区域
        private Rect mRect;
        // GradientDrawable支持使用渐变色来绘制图形，通常可以用作Button或是背景图形
        private GradientDrawable mDrawable;

        public RoundRectView(Context context) {
            super(context);
            setFocusable(true);
            initView();
        }

        /**
         * 初始化数据
         */
        private void initView() {
            mRect = new Rect(0, 0, DensityUtils.getScreenWidth(getContext()), 150);
            // orientation指定了渐变的方向，int[]colors指定渐变的颜色由colors数组指定，数组中的每个值为一个颜色。
            mDrawable = new GradientDrawable(
                    GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xFF64E0FF, 0xFF14BAFF});// 0xFF78d2fe
            // 设置Drawable的形状为矩形
            mDrawable.setShape(GradientDrawable.RECTANGLE);
            // 设置渐变的半径
            mDrawable.setGradientRadius((float) (Math.sqrt(2) * 120));
            for (int i = 0; i < 100; i++) {
                mRect.right = (int) (DensityUtils.getScreenWidth(getContext()) * i / 100f);
                invalidate();
            }
        }

        /**
         * 置图片四个角圆形半径
         */
        static void setCornersRadii(GradientDrawable drawable, float r0,
                                    float r1, float r2, float r3) {
            // 设置图片四个角圆形半径：1、2两个参数表示左上角，3、4表示右上角，5、6表示右下角，7、8表示左下角
            drawable.setCornerRadii(new float[]{r0, r0, r1, r1, r2, r2, r3, r3});
        }

        @Override
        protected void onDraw(Canvas canvas) {
            mDrawable.setBounds(mRect);
            float r = 16;
            canvas.save();
            canvas.translate(10, 10);//画面平移
            // 设置渐变模式：线性渐变
            mDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
            setCornersRadii(mDrawable, 0, 75, 75, 0);
            mDrawable.draw(canvas);
            canvas.restore();
//            canvas.save();
//            canvas.translate(10 + mRect.width() + 10, 10);
//            // 设置渐变模式：径抽渐变
//            mDrawable.setGradientType(GradientDrawable.RADIAL_GRADIENT);
//            setCornersRadii(mDrawable, 0, 0, r, r);
//            mDrawable.draw(canvas);
//            canvas.restore();
//            canvas.translate(0, mRect.height() + 10);
//            canvas.save();
//            canvas.translate(10, 10);
//            // 设置渐变模式：颜色渐变方向非环形，而是以某点为圆心呈扇形扫过。
//            mDrawable.setGradientType(GradientDrawable.SWEEP_GRADIENT);
//            setCornersRadii(mDrawable, 0, r, r, 0);
//            mDrawable.draw(canvas);
//            canvas.restore();
//            canvas.save();
//            canvas.translate(10 + mRect.width() + 10, 10);
//            mDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
//            setCornersRadii(mDrawable, r, 0, 0, r);
//            mDrawable.draw(canvas);
//            canvas.restore();
//            canvas.translate(0, mRect.height() + 10);
//            canvas.save();
//            canvas.translate(10, 10);
//            mDrawable.setGradientType(GradientDrawable.RADIAL_GRADIENT);
//            setCornersRadii(mDrawable, r, 0, r, 0);
//            mDrawable.draw(canvas);
//            canvas.restore();
//            canvas.save();
//            canvas.translate(10 + mRect.width() + 10, 10);
//            mDrawable.setGradientType(GradientDrawable.SWEEP_GRADIENT);
//            setCornersRadii(mDrawable, 0, r, 0, r);
//            mDrawable.draw(canvas);
//            canvas.restore();
        }
    }
}