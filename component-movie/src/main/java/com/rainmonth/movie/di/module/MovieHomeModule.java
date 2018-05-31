package com.rainmonth.movie.di.module;


import dagger.Module;
import dagger.Provides;

import com.rainmonth.common.di.scope.ActivityScope;
import com.rainmonth.movie.mvp.contract.MovieHomeContract;
import com.rainmonth.movie.mvp.model.MovieHomeModel;


@Module
public class MovieHomeModule {
    private MovieHomeContract.View view;

    /**
     * 构建MovieHomeModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MovieHomeModule(MovieHomeContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MovieHomeContract.View provideMovieHomeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MovieHomeContract.Model provideMovieHomeModel(MovieHomeModel model) {
        return model;
    }
}