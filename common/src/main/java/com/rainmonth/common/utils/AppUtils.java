package com.rainmonth.common.utils;

import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;

import com.socks.library.KLog;

/**
 * @author 张豪成
 * @date 2019-12-18 15:51
 */
public class AppUtils {
    public static final String TAG = AppUtils.class.getSimpleName();

    public static void requestKeepScreenOn(Context ctx) {
        if (ctx instanceof Activity) {
            requestKeepScreenOn((Activity) ctx);
        }
    }

    /**
     * 请求屏幕常亮
     *
     * @param ctx 上下文
     */
    public static void requestKeepScreenOn(Activity ctx) {
        try {
            if (ctx == null) {
                return;
            }
            ctx.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } catch (Exception e) {
            KLog.e(TAG, e);
        }
    }

    public static void cancelKeepScreenOn(Context ctx) {
        try {
            if (ctx instanceof Activity) {
                cancelKeepScreenOn((Activity) ctx);
            }
        } catch (Exception e) {
            KLog.e(TAG, e);
        }
    }

    /**
     * 取消屏幕常亮
     *
     * @param ctx 上下文
     */
    public static void cancelKeepScreenOn(Activity ctx) {
        try {
            if (ctx == null) {
                return;
            }
            ctx.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } catch (Exception e) {
            KLog.e(TAG, e);
        }
    }
}
