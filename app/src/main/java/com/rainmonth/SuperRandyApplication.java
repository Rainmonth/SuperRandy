package com.rainmonth;

import android.app.Application;

/**
 * Created by RandyZhang on 16/9/19.
 */
public class SuperRandyApplication extends Application {

    private static SuperRandyApplication application;
    private static int mainThreadId;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        mainThreadId = android.os.Process.myTid();
        configEnv();
        // todo activity 管理
    }

    public static SuperRandyApplication getApplication() {
        return application;
    }

    public static int getMainThreadId() {
        return mainThreadId;
    }

    /**
     * 配置环境
     */
    private void configEnv() {
        // todo 环境相关变量管理
    }
}
