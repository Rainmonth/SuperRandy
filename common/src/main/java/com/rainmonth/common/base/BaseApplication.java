package com.rainmonth.common.base;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.rainmonth.common.utils.ComponentUtils;

/**
 * 通用Application实现（无论是壳App还是独立的module，都可以继承该类
 * Created by RandyZhang on 2018/5/21.
 */
public abstract class BaseApplication extends Application {

    private BaseApplicationDelegate mBaseApplicationDelegate;

    @Override
    public void onCreate() {
        super.onCreate();
        this.mBaseApplicationDelegate = new BaseApplicationDelegate(this);
        this.mBaseApplicationDelegate.onCreate();

        boolean defaultProgress = getCurProcessName().equals(getPackageName());
        if (defaultProgress) {
            this.mBaseApplicationDelegate.onDefaultProgressCreate();
            this.onDefaultProgressCreate();
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        mBaseApplicationDelegate.onTerminate();
    }

    private void onDefaultProgressCreate() {

    }

    private String getCurProcessName() {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) ComponentUtils.getContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager != null) {
            for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                    .getRunningAppProcesses()) {
                if (appProcess.pid == pid) {
                    return appProcess.processName;
                }
            }
        }
        return "";
    }

    /**
     * 这是个模板方法，库module只负责实现，真正的调用发生在主module中
     * 初始化module对外提供的服务
     */
    public abstract void initModuleService();

}
