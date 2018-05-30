package com.rainmonth.di.component;

import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.common.di.scope.ActivityScope;
import com.rainmonth.di.module.SplashModule;
import com.rainmonth.mvp.ui.activity.SplashActivity;

import dagger.Component;

/**
 * Created by RandyZhang on 2018/5/30.
 */

@ActivityScope
@Component(modules = SplashModule.class, dependencies = AppComponent.class)
public interface SplashComponent {

    void inject(SplashActivity activity);
}
