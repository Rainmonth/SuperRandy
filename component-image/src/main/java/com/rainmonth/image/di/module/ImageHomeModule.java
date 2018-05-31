package com.rainmonth.image.di.module;


import com.rainmonth.common.di.scope.ActivityScope;
import com.rainmonth.image.mvp.contract.ImageHomeContract;
import com.rainmonth.image.mvp.model.ImageHomeModel;

import dagger.Module;
import dagger.Provides;


@Module
public class ImageHomeModule {
    private ImageHomeContract.View view;

    /**
     * 构建ImageHomeModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ImageHomeModule(ImageHomeContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ImageHomeContract.View provideImageHomeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ImageHomeContract.Model provideImageHomeModel(ImageHomeModel model) {
        return model;
    }
}