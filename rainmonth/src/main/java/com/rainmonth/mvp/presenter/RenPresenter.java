package com.rainmonth.mvp.presenter;

import com.rainmonth.common.base.mvp.BasePresenter;
import com.rainmonth.common.di.scope.FragmentScope;
import com.rainmonth.common.http.CommonSubscriber;
import com.rainmonth.common.http.PageResult;
import com.rainmonth.common.http.Result;
import com.rainmonth.utils.RxUtils;
import com.rainmonth.mvp.contract.RenContract;
import com.rainmonth.mvp.model.bean.ArticleBean;
import com.rainmonth.mvp.model.bean.BannerBean;
import com.rainmonth.mvp.model.bean.TestBean;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.functions.BiFunction;

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

    public void test() {
        Flowable<Result<List<BannerBean>>> resultFlowable = mModel.getBannerList(1, 10, 6);
        Flowable<PageResult<ArticleBean>> pageResultFlowable = mModel.getArticleList(1, 10);
        Flowable<TestBean> flowable = Flowable.zip(resultFlowable, pageResultFlowable, new BiFunction<Result<List<BannerBean>>, PageResult<ArticleBean>, TestBean>() {
            @Override
            public TestBean apply(Result<List<BannerBean>> listResult, PageResult<ArticleBean> articleBeanPageResult) throws Exception {
                TestBean testBean = new TestBean();
                if (listResult.isSuccess()) {
                    testBean.setBannerBeanList(listResult.getData());
                }
                if (articleBeanPageResult.isSuccess()) {
                    testBean.setArticleBeanList(articleBeanPageResult.getData().getList());
                }

                return testBean;
            }
        });

        addSubscribe(flowable.compose(RxUtils.<TestBean>getFlowableTransformer()).subscribeWith(new CommonSubscriber<TestBean>(mView) {
            @Override
            public void onNext(TestBean testBean) {
                mView.test(testBean);
            }
        }));
    }
}
