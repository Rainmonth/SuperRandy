package com.rainmonth.movie.mvp.presenter;

import com.rainmonth.common.base.mvp.BasePresenter;
import com.rainmonth.common.di.scope.ActivityScope;
import com.rainmonth.movie.mvp.contract.MovieHomeContract;

import javax.inject.Inject;


@ActivityScope
public class MovieHomePresenter extends
        BasePresenter<MovieHomeContract.Model, MovieHomeContract.View> {

    @Inject
    public MovieHomePresenter(MovieHomeContract.Model model, MovieHomeContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
