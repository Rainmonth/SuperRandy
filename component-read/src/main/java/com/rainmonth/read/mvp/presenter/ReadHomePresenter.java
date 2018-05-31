package com.rainmonth.read.mvp.presenter;

import com.rainmonth.common.base.mvp.BasePresenter;
import com.rainmonth.common.di.scope.ActivityScope;
import com.rainmonth.read.mvp.contract.ReadHomeContract;

import javax.inject.Inject;


@ActivityScope
public class ReadHomePresenter extends BasePresenter<ReadHomeContract.Model, ReadHomeContract.View> {

    @Inject
    public ReadHomePresenter(ReadHomeContract.Model model, ReadHomeContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
