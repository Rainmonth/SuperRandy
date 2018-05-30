package com.rainmonth.common.di.module;

import android.app.Application;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


/**
 * 用来提供一些全局配置的单例对象，如网络请求、图片加载、Json解析等
 */
@Module
public class AppModule {

  private Application mApplication;

  public AppModule(Application application) {
    this.mApplication = application;
  }

  @Singleton
  @Provides
  public Application provideApplication() {
    return mApplication;
  }

  @Singleton
  @Provides
  public Gson provideGson() {
    return new Gson();
  }

//  @Singleton
//  @Provides
//  public IRepositoryManager provideRepositoryManager(RepositoryManager repositoryManager) {
//    return repositoryManager;
//  }

//  @Provides
//  public RxPermissions provideRxPermissions(AppManager appManager) {
//    return new RxPermissions(appManager.getCurrentActivity());
//  }
}
