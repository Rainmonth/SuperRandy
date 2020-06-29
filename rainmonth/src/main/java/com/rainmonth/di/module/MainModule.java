package com.rainmonth.di.module;

import com.rainmonth.common.di.scope.ActivityScope;
import com.rainmonth.mvp.contract.MainContract;
import com.rainmonth.mvp.model.MainModel;

import dagger.Module;
import dagger.Provides;

/**
 * 壳子主Moudle
 * Created by RandyZhang on 2018/5/24.
 */
@Module
public class MainModule {
    private MainContract.View view;

    public MainModule(MainContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MainContract.View provideMainView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MainContract.Model provideMainModel(MainModel model) {
        return model;
    }
}
