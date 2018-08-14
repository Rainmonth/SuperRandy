package com.rainmonth.image.config;

import android.app.Application;
import android.content.Context;

import com.rainmonth.common.base.BaseApplicationDelegate;
import com.rainmonth.common.di.module.GlobeConfigModule;
import com.rainmonth.common.integration.ConfigModule;
import com.rainmonth.common.integration.IRepositoryManager;
import com.rainmonth.image.api.UPhotoApi;
import com.rainmonth.image.api.UUserApi;

import java.util.List;

/**
 * @desprition: 图形组件配置
 * @author: RandyZhang
 * @date: 2018/8/8 上午7:20
 */
public class ImageAppConfig implements ConfigModule{
    @Override
    public void applyOptions(Context context, GlobeConfigModule.Builder builder) {

    }

    @Override
    public void registerComponents(Context context, IRepositoryManager repositoryManager) {
        repositoryManager.injectRetrofitService(UUserApi.class);
        repositoryManager.injectRetrofitService(UPhotoApi.class);
    }

    @Override
    public void injectAppLifecycle(Context context, List<BaseApplicationDelegate.Lifecycle> lifecycleList) {

    }

    @Override
    public void injectActivityLifecycle(Context context, List<Application.ActivityLifecycleCallbacks> lifecycleList) {

    }
}