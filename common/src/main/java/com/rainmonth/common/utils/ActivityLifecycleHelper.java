package com.rainmonth.common.utils;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.util.Stack;

/**
 * Activity 生命周期管理及常用Activity工具类
 *
 * @author 张豪成
 * @date 2019-05-30 10:17
 */
public class ActivityLifecycleHelper implements Application.ActivityLifecycleCallbacks {

    private Stack<Activity> mActivityStack = new Stack<>();

    public ActivityLifecycleHelper(Stack<Activity> mActivityStack) {
        this.mActivityStack = mActivityStack;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    private void addActivity(Activity activity) {
        if (mActivityStack == null) {
            mActivityStack = new Stack<>();
        }
        mActivityStack.push(activity);
    }

    private void removeActivity(Activity activity) {
        if (activity != null && mActivityStack != null) {
            mActivityStack.remove(activity);
        }
    }
}
