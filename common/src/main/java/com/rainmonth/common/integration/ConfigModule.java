package com.rainmonth.common.integration;

import android.app.Application;
import android.content.Context;

import com.rainmonth.common.base.BaseApplicationDelegate;
import com.rainmonth.common.di.module.GlobeConfigModule;

import java.util.List;

/**
 * 框架配置接口
 * Created by RandyZhang on 2018/6/1.
 */

public interface ConfigModule {
    /**
     * 应用框架配置选项
     *
     * @param context
     * @param builder
     */
    void applyOptions(Context context, GlobeConfigModule.Builder builder);

    /**
     * 注入数据操作（网络获取、缓存获取、数据库获取）服务
     *
     * @param context
     * @param repositoryManager
     */
    void registerComponents(Context context, IRepositoryManager repositoryManager);

    /**
     * 在Application生命周期注入操作
     *
     * @param context
     * @param lifecycleList
     */
    void injectAppLifecycle(Context context, List<BaseApplicationDelegate.Lifecycle> lifecycleList);

    /**
     * 在Activity生命周期注入操作
     *
     * @param context
     * @param lifecycleList
     */
    void injectActivityLifecycle(Context context,
                                 List<Application.ActivityLifecycleCallbacks> lifecycleList);
}
