package com.rainmonth.image.di.component;

import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.common.di.scope.ActivityScope;
import com.rainmonth.image.di.module.SearchModule;
import com.rainmonth.image.mvp.ui.search.SearchActivity;

import dagger.Component;

/**
 * @author: Randy Zhang
 * @description: ${DESC}
 * @created: 2018/8/15
 **/
@ActivityScope
@Component(modules = {SearchModule.class}, dependencies = AppComponent.class)
public interface SearchComponent {
    void inject(SearchActivity activity);
}
