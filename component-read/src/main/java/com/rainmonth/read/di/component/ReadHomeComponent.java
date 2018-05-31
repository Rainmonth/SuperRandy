package com.rainmonth.read.di.component;

import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.common.di.scope.ActivityScope;
import com.rainmonth.read.di.module.ReadHomeModule;
import com.rainmonth.read.mvp.ui.activity.ReadHomeActivity;

import dagger.Component;

@ActivityScope
@Component(modules = ReadHomeModule.class, dependencies = AppComponent.class)
public interface ReadHomeComponent {
    void inject(ReadHomeActivity activity);
}