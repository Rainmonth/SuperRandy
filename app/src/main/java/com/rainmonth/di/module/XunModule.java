package com.rainmonth.di.module;

import com.rainmonth.common.di.scope.FragmentScope;
import com.rainmonth.mvp.contract.XunContract;
import com.rainmonth.mvp.model.XunModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by RandyZhang on 2018/5/31.
 */
@Module
public class XunModule {
    private XunContract.View view;

    public XunModule(XunContract.View view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    XunContract.View provideXunView() {
        return view;
    }

    @FragmentScope
    @Provides
    XunContract.Model provideXunModel(XunModel model) {
        return model;
    }
}
