package com.rainmonth.image.di.component;

import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.common.di.scope.ActivityScope;
import com.rainmonth.image.di.module.ImageHomeModule;
import com.rainmonth.image.mvp.ui.activity.ImageHomeActivity;

import dagger.Component;

@ActivityScope
@Component(modules = ImageHomeModule.class, dependencies = AppComponent.class)
public interface ImageHomeComponent {
    void inject(ImageHomeActivity activity);
}