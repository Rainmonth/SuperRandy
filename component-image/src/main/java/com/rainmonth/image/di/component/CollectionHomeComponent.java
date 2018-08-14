package com.rainmonth.image.di.component;

import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.common.di.scope.ActivityScope;
import com.rainmonth.image.di.module.CollectionHomeModule;
import com.rainmonth.image.mvp.ui.activity.CollectionHomeActivity;

import dagger.Component;

@ActivityScope
@Component(modules = {CollectionHomeModule.class}, dependencies = AppComponent.class)
public interface CollectionHomeComponent {
    void inject(CollectionHomeActivity activity);
}