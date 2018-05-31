package com.rainmonth.read.di.module;

import com.rainmonth.common.di.scope.ActivityScope;
import com.rainmonth.read.mvp.contract.ReadHomeContract;
import com.rainmonth.read.mvp.model.ReadHomeModel;

import dagger.Module;
import dagger.Provides;


@Module
public class ReadHomeModule {
    private ReadHomeContract.View view;

    /**
     * 构建ReadHomeModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ReadHomeModule(ReadHomeContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ReadHomeContract.View provideReadHomeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ReadHomeContract.Model provideReadHomeModel(ReadHomeModel model) {
        return model;
    }
}