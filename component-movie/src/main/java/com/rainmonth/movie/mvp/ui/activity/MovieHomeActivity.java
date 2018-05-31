package com.rainmonth.movie.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.movie.R;
import com.rainmonth.movie.di.component.DaggerMovieHomeComponent;
import com.rainmonth.movie.di.module.MovieHomeModule;
import com.rainmonth.movie.mvp.contract.MovieHomeContract;
import com.rainmonth.movie.mvp.presenter.MovieHomePresenter;
import com.rainmonth.router.RouterConstant;

@Route(path = RouterConstant.PATH_MOVIE_HOME)
public class MovieHomeActivity extends BaseActivity<MovieHomePresenter>
        implements MovieHomeContract.View {

    @Override
    public void initToolbar() {

    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMovieHomeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .movieHomeModule(new MovieHomeModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.movie_activity_main;
    }

    @Override
    protected void initViewsAndEvents() {

    }
}
