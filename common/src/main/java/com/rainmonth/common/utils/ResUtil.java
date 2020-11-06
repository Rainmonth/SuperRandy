package com.rainmonth.common.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rainmonth.common.R;

/**
 * 提供方便的获取各种资源的工具类
 *
 * @author 张豪成
 * @date 2019-11-20 14:27
 */
public class ResUtil {

    @SuppressLint("StaticFieldLeak")
    private static Context mAppCtx;

    public static void init(Context context) {
        if (context != null) {
            mAppCtx = context.getApplicationContext();
        }
    }

    public static String getString(int strResId) {
        if (mAppCtx != null) {
            return mAppCtx.getString(strResId);
        }
        return "";
    }

    public static int getColor(int colorResId) {
        if (mAppCtx != null && mAppCtx.getResources() != null) {
            return mAppCtx.getResources().getColor(colorResId);
        }
        return 0;
    }

    public static Drawable getDrawable(int drawableResId) {
        if (mAppCtx != null && mAppCtx.getResources() != null) {
            return mAppCtx.getResources().getDrawable(drawableResId);
        }
        return mAppCtx.getResources().getDrawable(R.drawable.common_defalut_drawable);
    }
}
