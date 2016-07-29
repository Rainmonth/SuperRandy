package com.rainmonth.utils;

import android.content.Context;

/**
 * Created by RandyZhang on 16/7/14.
 */
public class CommonUtils {
    public static int getDrawableIdByName(Context context, String drawableName) {

        return context.getResources().getIdentifier(drawableName, "drawable", context.getPackageName());
    }
}
