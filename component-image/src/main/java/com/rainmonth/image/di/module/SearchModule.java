package com.rainmonth.image.di.module;

import com.rainmonth.common.di.scope.ActivityScope;
import com.rainmonth.image.mvp.contract.SearchContract;
import com.rainmonth.image.mvp.model.SearchModel;

import dagger.Module;
import dagger.Provides;

@Module
public class SearchModule {
    private SearchContract.View view;

    /**
     * 构建ImageHomeModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public SearchModule(SearchContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    SearchContract.View provideSearchView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    SearchContract.Model provideSearchModel(SearchModel model) {
        return model;
    }
}