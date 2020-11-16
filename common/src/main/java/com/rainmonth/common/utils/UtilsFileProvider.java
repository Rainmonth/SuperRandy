package com.rainmonth.common.utils;

import android.app.Application;

import androidx.core.content.FileProvider;

/**
 * 用来初始化{@link Utils}中的Application对象的
 */
public class UtilsFileProvider extends FileProvider {

    @Override
    public boolean onCreate() {
        Utils.init((Application) getContext().getApplicationContext());
        return true;
    }
}
