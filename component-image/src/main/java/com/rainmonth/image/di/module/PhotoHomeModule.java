package com.rainmonth.image.di.module;


import com.rainmonth.common.di.scope.ActivityScope;
import com.rainmonth.image.mvp.contract.PhotoHomeContract;
import com.rainmonth.image.mvp.model.PhotoHomeModel;

import dagger.Module;
import dagger.Provides;


@Module
public class PhotoHomeModule {
    private PhotoHomeContract.View view;

    /**
     * 构建ImageHomeModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public PhotoHomeModule(PhotoHomeContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    PhotoHomeContract.View providePhotoHomeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    PhotoHomeContract.Model providePhotoHomeModel(PhotoHomeModel model) {
        return model;
    }
}