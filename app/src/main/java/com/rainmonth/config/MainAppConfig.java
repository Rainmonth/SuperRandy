package com.rainmonth.config;

import android.app.Application;
import android.content.Context;

import com.rainmonth.api.AlbumService;
import com.rainmonth.api.ArticleService;
import com.rainmonth.api.BannerService;
import com.rainmonth.api.NavService;
import com.rainmonth.api.UserService;
import com.rainmonth.common.base.BaseApplicationDelegate;
import com.rainmonth.common.di.module.GlobeConfigModule;
import com.rainmonth.common.integration.ConfigModule;
import com.rainmonth.common.integration.IRepositoryManager;

import java.util.List;

/**
 * 该类用来声明数据请求服务、数据库服务等的配置，需要用
 * Created by RandyZhang on 2018/6/11.
 */
public class MainAppConfig implements ConfigModule {
    @Override
    public void applyOptions(Context context, GlobeConfigModule.Builder builder) {

    }

    @Override
    public void registerComponents(Context context, IRepositoryManager repositoryManager) {
        repositoryManager.injectRetrofitService(BannerService.class);
        repositoryManager.injectRetrofitService(UserService.class);
        repositoryManager.injectRetrofitService(ArticleService.class);
        repositoryManager.injectRetrofitService(AlbumService.class);
        repositoryManager.injectRetrofitService(NavService.class);
    }

    @Override
    public void injectAppLifecycle(Context context, List<BaseApplicationDelegate.Lifecycle> lifecycleList) {

    }

    @Override
    public void injectActivityLifecycle(Context context, List<Application.ActivityLifecycleCallbacks> lifecycleList) {

    }
}
