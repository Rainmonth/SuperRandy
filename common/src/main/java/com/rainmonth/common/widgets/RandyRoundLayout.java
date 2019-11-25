package com.rainmonth.common.widgets;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * @author 张豪成
 * @date 2019-11-25 17:46
 */
public class RandyRoundLayout extends RandyRateLayout {
    public RandyRoundLayout(@NonNull Context context) {
        super(context);
    }

    public RandyRoundLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RandyRoundLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RandyRoundLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void initAttr(Context context, AttributeSet attrs) {
        super.initAttr(context, attrs);
    }

    @CallSuper
    @Override
    protected void initView(Context context) {
        super.initView(context);
    }
}
