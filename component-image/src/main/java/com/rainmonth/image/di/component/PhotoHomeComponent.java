package com.rainmonth.image.di.component;

import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.common.di.scope.ActivityScope;
import com.rainmonth.image.di.module.PhotoHomeModule;
import com.rainmonth.image.mvp.ui.activity.PhotoHomeActivity;

import dagger.Component;

@ActivityScope
@Component(modules = {PhotoHomeModule.class}, dependencies = AppComponent.class)
public interface PhotoHomeComponent {
    void inject(PhotoHomeActivity activity);
}