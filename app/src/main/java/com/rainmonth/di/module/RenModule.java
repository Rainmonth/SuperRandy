package com.rainmonth.di.module;

import com.rainmonth.common.di.scope.FragmentScope;
import com.rainmonth.mvp.contract.RenContract;
import com.rainmonth.mvp.model.RenModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by RandyZhang on 2018/5/31.
 */
@Module
public class RenModule {
    private RenContract.View view;

    public RenModule(RenContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    RenContract.View provideRenView() {
        return view;
    }

    @FragmentScope
    @Provides
    RenContract.Model provideRenModel(RenModel model) {
        return model;
    }
}
