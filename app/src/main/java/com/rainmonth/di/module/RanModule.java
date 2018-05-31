package com.rainmonth.di.module;

import com.rainmonth.common.di.scope.FragmentScope;
import com.rainmonth.mvp.contract.RanContract;
import com.rainmonth.mvp.model.RanModel;

import dagger.Module;
import dagger.Provides;

/**
 * Ran module
 * Created by RandyZhang on 2018/5/31.
 */
@Module
public class RanModule {
    private RanContract.View view;

    public RanModule(RanContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    RanContract.View provideRanView() {
        return view;
    }

    @FragmentScope
    @Provides
    RanContract.Model provideRanModel(RanModel model) {
        return model;
    }
}
