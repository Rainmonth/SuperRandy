package com.rainmonth.image.mvp.ui.photo;

import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.common.di.scope.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(modules = {PhotoDetailModule.class}, dependencies = AppComponent.class)
public interface PhotoDetailComponent {
    void inject(PhotoDetailActivity activity);
}