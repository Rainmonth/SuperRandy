package com.rainmonth.di.module;

import com.rainmonth.common.di.scope.FragmentScope;
import com.rainmonth.mvp.contract.PursueContract;
import com.rainmonth.mvp.model.PursueModel;

import dagger.Module;
import dagger.Provides;

/**
 * @desprition: 追 Module定义
 * @author: RandyZhang
 * @date: 2018/6/26 下午2:59
 */
@Module
public class PursueModule {
    private PursueContract.View view;

    public PursueModule(PursueContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    PursueContract.View providePursueView() {
        return view;
    }

    @FragmentScope
    @Provides
    PursueContract.Model providePursueModel(PursueModel model) {
        return model;
    }
}