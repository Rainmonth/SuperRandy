package com.rainmonth.di.component;

import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.common.di.scope.ActivityScope;
import com.rainmonth.di.module.MainModule;
import com.rainmonth.mvp.ui.activity.MainActivity;

import dagger.Component;

/**
 * MainComponent
 * Created by RandyZhang on 2018/5/24.
 */
@ActivityScope
@Component(modules = MainModule.class, dependencies = AppComponent.class)
public interface MainComponent {
    void inject(MainActivity activity);
}
