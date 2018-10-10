package com.rainmonth.image.mvp.ui.search;

import com.rainmonth.common.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class SearchResultModule {
    private SearchResultContract.View view;

    /**
     * 构建ImageHomeModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public SearchResultModule(SearchResultContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    SearchResultContract.View provideSearchResultlView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    SearchResultContract.Model provideSearchResultModel(SearchResultModel model) {
        return model;
    }
}
