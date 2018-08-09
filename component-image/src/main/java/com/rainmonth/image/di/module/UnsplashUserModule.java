package com.rainmonth.image.di.module;

import com.rainmonth.common.di.scope.ActivityScope;
import com.rainmonth.image.mvp.contract.UnsplashUserContract;
import com.rainmonth.image.mvp.model.UnsplashUserModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by RandyZhang on 2018/8/9.
 */
@Module
public class UnsplashUserModule {
    private UnsplashUserContract.View view;

    public UnsplashUserModule(UnsplashUserContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    UnsplashUserContract.View provideUnsplashUserView() {
        return view;
    }

    @ActivityScope
    @Provides
    UnsplashUserContract.Model provideUnsplashUserModel(UnsplashUserModel model) {
        return model;
    }
}
