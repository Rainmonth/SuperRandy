package com.rainmonth.common.base;

import android.app.Application;

import com.rainmonth.common.di.component.AppComponent;

/**
 * Created by RandyZhang on 2018/5/21.
 */

public class BaseApplication extends Application {

    protected AppComponent mAppComponent;
    @Override
    public void onCreate() {
        super.onCreate();


    }
}
