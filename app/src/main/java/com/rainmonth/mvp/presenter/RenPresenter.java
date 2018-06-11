package com.rainmonth.mvp.presenter;

import com.rainmonth.common.base.mvp.BasePresenter;
import com.rainmonth.common.di.scope.FragmentScope;
import com.rainmonth.common.http.CommonSubscriber;
import com.rainmonth.common.utils.RxUtils;
import com.rainmonth.mvp.contract.RenContract;
import com.rainmonth.mvp.model.bean.BannerBean;
import com.rainmonth.common.http.Result;

import java.util.List;

import javax.inject.Inject;

/**
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
        addSubscribe(mModel.getBannerList()
                .compose(RxUtils.<Result<List<BannerBean>>>getFlowableTransformer())// 进行线程切换
                .subscribeWith(new CommonSubscriber<Result<List<BannerBean>>>(mView) {
                    @Override
                    public void onNext(Result<List<BannerBean>> baseResponse) {
                        mView.initHomeBanners(baseResponse.getData());
                    }
                }));
//        mView.initHomeBanners(mModel.getBannerList());
    }
}
