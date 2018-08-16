package com.rainmonth.music.config;

import android.app.Application;
import android.content.Context;

import com.rainmonth.common.base.BaseApplicationDelegate;
import com.rainmonth.common.di.module.GlobeConfigModule;
import com.rainmonth.common.integration.ConfigModule;
import com.rainmonth.common.integration.IRepositoryManager;

import java.util.List;

/**
 * @author: Randy Zhang
 * @description: 音乐应用配置
 * @created: 2018/8/16
 **/
public class MusicAppConfig implements ConfigModule {

    @Override
    public void applyOptions(Context context, GlobeConfigModule.Builder builder) {

    }

    @Override
    public void registerComponents(Context context, IRepositoryManager repositoryManager) {

    }

    @Override
    public void injectAppLifecycle(Context context, List<BaseApplicationDelegate.Lifecycle> lifecycleList) {

    }

    @Override
    public void injectActivityLifecycle(Context context, List<Application.ActivityLifecycleCallbacks> lifecycleList) {

    }
}