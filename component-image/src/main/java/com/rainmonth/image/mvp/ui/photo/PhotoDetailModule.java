package com.rainmonth.image.mvp.ui.photo;

import com.rainmonth.common.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class PhotoDetailModule {
    private PhotoDetailContract.View view;

    /**
     * 构建ImageHomeModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public PhotoDetailModule(PhotoDetailContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    PhotoDetailContract.View providePhotoDetailView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    PhotoDetailContract.Model providePhotoDetailModel(PhotoDetailModel model) {
        return model;
    }
}
