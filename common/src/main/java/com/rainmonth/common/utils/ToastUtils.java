package com.rainmonth.common.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * toast 提示，dialog提示
 * Created by RandyZhang on 16/9/13.
 */
public class ToastUtils {

    public static void showToast(Context context, String message) {
        showLongToast(context, message);
    }

    public static void showLongToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void showShortToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
