package com.rainmonth.image.di.module;


import com.rainmonth.common.di.scope.ActivityScope;
import com.rainmonth.image.mvp.contract.CollectionHomeContract;
import com.rainmonth.image.mvp.model.CollectionHomeModel;

import dagger.Module;
import dagger.Provides;


@Module
public class CollectionHomeModule {
    private CollectionHomeContract.View view;

    /**
     * 构建ImageHomeModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public CollectionHomeModule(CollectionHomeContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    CollectionHomeContract.View provideCollectionHomeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    CollectionHomeContract.Model provideCollectionHomeModel(CollectionHomeModel model) {
        return model;
    }
}