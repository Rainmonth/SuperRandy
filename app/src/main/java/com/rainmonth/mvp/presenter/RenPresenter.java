package com.rainmonth.mvp.presenter;

import com.rainmonth.common.base.mvp.BasePresenter;
import com.rainmonth.common.di.scope.ActivityScope;
import com.rainmonth.common.di.scope.FragmentScope;
import com.rainmonth.mvp.contract.RenContract;

import javax.inject.Inject;

/**
 *
 * Created by RandyZhang on 16/7/5.
 */
@FragmentScope
public class RenPresenter extends BasePresenter<RenContract.Model, RenContract.View> {

    @Inject
    public RenPresenter(RenContract.Model model, RenContract.View view) {
        super(model, view);
    }

    public void init() {
        mView.initContentList(mModel.getArticleList());
        mView.initHomeBanners(mModel.getBannerList());
    }
}
