package com.rainmonth.common.base;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.rainmonth.common.utils.ComponentUtils;

/**
 * 通用Application实现（无论是壳App还是独立的module，都可以继承该类
 * Created by RandyZhang on 2018/5/21.
 */
public class BaseApplication extends Application {

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
    public void onTerminate() {
        super.onTerminate();
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

}
