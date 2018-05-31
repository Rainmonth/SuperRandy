package com.rainmonth.image.mvp.presenter;

import com.rainmonth.common.base.mvp.BasePresenter;
import com.rainmonth.common.di.scope.ActivityScope;
import com.rainmonth.image.mvp.contract.ImageHomeContract;

import javax.inject.Inject;


@ActivityScope
public class ImageHomePresenter extends BasePresenter<ImageHomeContract.Model, ImageHomeContract.View> {

    @Inject
    public ImageHomePresenter(ImageHomeContract.Model model, ImageHomeContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
