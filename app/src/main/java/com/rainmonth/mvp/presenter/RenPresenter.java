package com.rainmonth.mvp.presenter;

import com.rainmonth.common.base.mvp.BasePresenter;
import com.rainmonth.common.di.scope.FragmentScope;
import com.rainmonth.common.http.CommonSubscriber;
import com.rainmonth.common.http.PageResult;
import com.rainmonth.common.http.Result;
import com.rainmonth.common.utils.RxUtils;
import com.rainmonth.mvp.contract.RenContract;
import com.rainmonth.mvp.model.bean.ArticleBean;
import com.rainmonth.mvp.model.bean.BannerBean;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by RandyZhang on 16/7/5.
 */
@FragmentScope
public class RenPresenter extends BasePresenter<RenContract.Model, RenContract.View> {

    @Inject
    RenPresenter(RenContract.Model model, RenContract.View view) {
        super(model, view);
    }

    public void getBannerList(int page, int pageSize, int type) {
        addSubscribe(mModel.getBannerList(page, pageSize, type)
                .compose(RxUtils.<Result<List<BannerBean>>>getFlowableTransformer())// 进行线程切换
                .subscribeWith(new CommonSubscriber<Result<List<BannerBean>>>(mView) {
                    @Override
                    public void onNext(Result<List<BannerBean>> baseResponse) {
                        mView.initHomeBanners(baseResponse.getData());
                    }
                }));
    }

    public void getArticleList(int page, int pageSize) {
        addSubscribe(mModel.getArticleList(page, pageSize)
                .compose(RxUtils.<PageResult<ArticleBean>>getFlowableTransformer())
                .subscribeWith(new CommonSubscriber<PageResult<ArticleBean>>(mView) {
                    @Override
                    public void onNext(PageResult<ArticleBean> articleBeanPageResult) {
                        if (articleBeanPageResult.isSuccess()) {
                            mView.initContentList(articleBeanPageResult.getData());
                        } else {
                            mView.toast(articleBeanPageResult.getMessage());
                        }
                    }
                }));
    }
}
