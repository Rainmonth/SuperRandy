package com.rainmonth.di.module;

import com.rainmonth.common.di.scope.ActivityScope;
import com.rainmonth.mvp.contract.SplashContract;
import com.rainmonth.mvp.model.SplashModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by RandyZhang on 2018/5/30.
 */

@Module
public class SplashModule {
    private SplashContract.View view;

    public SplashModule(SplashContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    SplashContract.View provideSplashView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    SplashContract.Model provideSplashModel(SplashModel model) {
        return model;
    }
}
