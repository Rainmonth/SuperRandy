package com.rainmonth.common.widgets.test;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.rainmonth.common.R;
import com.rainmonth.common.utils.DensityUtils;
import com.socks.library.KLog;

/**
 * @author RandyZhang
 * @date 2020/10/15 11:29 AM
 */
public class RandyPercentageLayout extends FrameLayout {
    private static final String TAG = RandyPercentageLayout.class.getSimpleName();
    /**
     * 宽度占屏幕百分比， -1表示不根据百分比计算
     */
    private float mWidthPercentage = -1;
    /**
     * 宽高比
     */
    private float mRate = 32 / 25f;


    public RandyPercentageLayout(Context context) {
        super(context);
        initAttr(context, null);
        initView(context);
    }

    public RandyPercentageLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs);
        initView(context);
    }

    public RandyPercentageLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
        initView(context);
    }

    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RandyPercentageLayout);
        try {
            mWidthPercentage = ta.getFloat(R.styleable.RandyPercentageLayout_rpl_percentage, -1);
            mRate = ta.getFloat(R.styleable.RandyPercentageLayout_rpl_rage, mRate);

        } finally {
            if (ta != null) {
                ta.recycle();
            }
        }
    }

    protected void initView(Context context) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int sw = getResources().getDisplayMetrics().widthPixels;
        KLog.d(TAG, "onMeasure: " + "screenWidth=" + getResources().getDisplayMetrics().widthPixels + ",screeHeight=" + getResources().getDisplayMetrics().heightPixels);
        KLog.d(TAG, "onMeasure: " + "w=" + getWidth() + ",h=" + getHeight() + ",mw=" + getMeasuredWidth() + ",mh=" + getMeasuredHeight());
        if (mWidthPercentage == -1) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }

        if (mWidthPercentage > 0) {
            mWidthPercentage = Math.min(mWidthPercentage, 1);
        }
        final int width = MeasureSpec.getSize(widthMeasureSpec);
        KLog.e(TAG, "onMeasure: " + "w=" + getWidth() + ",h=" + getHeight() + ",mw=" + getMeasuredWidth() + ",mh=" + getMeasuredHeight() + ",width=" + width);
        int newWidth = Math.round(sw * mWidthPercentage);

        if (mRate == -1) {
            super.onMeasure(MeasureSpec.makeMeasureSpec(newWidth, MeasureSpec.EXACTLY), heightMeasureSpec);
            KLog.e(TAG, "onMeasure: " + "w=" + getWidth() + ",h=" + getHeight() + ",mw=" + getMeasuredWidth() + ",mh=" + getMeasuredHeight() + ",width=" + width);
            setMeasuredDimension(newWidth, MeasureSpec.getSize(heightMeasureSpec));
            KLog.e(TAG, "onMeasure: " + "w=" + getWidth() + ",h=" + getHeight() + ",mw=" + getMeasuredWidth() + ",mh=" + getMeasuredHeight() + ",width=" + width);
            return;
        }

        if (mRate > 0) {
            int newHeight = Math.round(newWidth * mRate);
            super.onMeasure(MeasureSpec.makeMeasureSpec(newWidth, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(newHeight, MeasureSpec.EXACTLY));
            KLog.e(TAG, "onMeasure: " + "w=" + getWidth() + ",h=" + getHeight() + ",mw=" + getMeasuredWidth() + ",mh=" + getMeasuredHeight() + ",width=" + width);
            setMeasuredDimension(newWidth, newHeight);
            KLog.e(TAG, "onMeasure: " + "w=" + getWidth() + ",h=" + getHeight() + ",mw=" + getMeasuredWidth() + ",mh=" + getMeasuredHeight() + ",width=" + width);
            return;
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }
}
