package com.rainmonth.movie.mvp.model;

import com.rainmonth.common.base.mvp.BaseModel;
import com.rainmonth.common.di.scope.ActivityScope;
import com.rainmonth.movie.mvp.contract.MovieHomeContract;

import javax.inject.Inject;


@ActivityScope
public class MovieHomeModel extends BaseModel implements MovieHomeContract.Model {
    @Inject
    public MovieHomeModel() {
    }

}