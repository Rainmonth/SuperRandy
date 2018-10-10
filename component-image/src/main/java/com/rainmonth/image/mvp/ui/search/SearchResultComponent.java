package com.rainmonth.image.mvp.ui.search;

import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.common.di.scope.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(modules = {SearchResultModule.class}, dependencies = AppComponent.class)
public interface SearchResultComponent {
    void inject(SearchResultActivity activity);
}