package com.rainmonth.common.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;

import com.rainmonth.common.di.component.AppComponent;

/**
 * 组件工具类（主要是提供AppComponent相关的方法）
 * Created by RandyZhang on 2018/5/30.
 */

public class ComponentUtils {
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    /**
     * 为了配合tinker的使用，将其抽取为静态变量
     */
    private static AppComponent appComponent;

    private ComponentUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(@NonNull Context context) {
        ComponentUtils.context = context.getApplicationContext();
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (context != null) {
            return context;
        }
        throw new NullPointerException("u should init first");
    }

    public static void initAppComponent(@NonNull AppComponent appComponent) {
        ComponentUtils.appComponent = appComponent;
    }

    public static AppComponent getAppComponent() {
        if (appComponent != null) {
            return appComponent;
        }
        throw new NullPointerException("appComponent need to be initialized");
    }
}
