package com.rainmonth.common.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.rainmonth.common.R;

/**
 * 比例布局（提供一个设置宽高比属性）
 *
 * @author 张豪成
 * @date 2019-11-23 11:37
 */
public class RandyRateLayout extends FrameLayout {
    /**
     * 宽高比（宽：高），默认不设置
     */
    private float mRate = -1;

    public RandyRateLayout(@NonNull Context context) {
        this(context, 1);
    }

    public RandyRateLayout(@NonNull Context context, float rate) {
        super(context);
        mRate = rate;
        initView(context);
    }

    public RandyRateLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs);
        initView(context);
    }

    public RandyRateLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
        initView(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RandyRateLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttr(context, attrs);
        initView(context);
    }

    protected void initView(Context context) {

    }

    protected void initAttr(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RandyRateLayout);
        mRate = ta.getFloat(R.styleable.RandyRateLayout_rate, mRate);
        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mRate <= 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        final int width = MeasureSpec.getSize(widthMeasureSpec);
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(Math.round(width * mRate), MeasureSpec.EXACTLY));
        // 子view测量完，再重新调整宽高，例如一些特殊场景LinearLayout的weight
        setMeasuredDimension(width, Math.round(width * mRate));
    }
}
