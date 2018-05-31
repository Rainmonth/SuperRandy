package com.rainmonth.movie.di.component;

import dagger.Component;

import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.common.di.scope.ActivityScope;
import com.rainmonth.movie.di.module.MovieHomeModule;

import com.rainmonth.movie.mvp.ui.activity.MovieHomeActivity;

@ActivityScope
@Component(modules = MovieHomeModule.class, dependencies = AppComponent.class)
public interface MovieHomeComponent {
    void inject(MovieHomeActivity activity);
}