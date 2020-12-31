package com.rainmonth.mvp.presenter;

import com.rainmonth.common.base.mvp.BasePresenter;
import com.rainmonth.common.di.scope.FragmentScope;
import com.rainmonth.common.http.CommonSubscriber;
import com.rainmonth.common.http.PageResult;
import com.rainmonth.utils.RxUtils;
import com.rainmonth.mvp.contract.PursueContract;
import com.rainmonth.mvp.model.bean.PursueBean;

import javax.inject.Inject;

/**
 * Created by RandyZhang on 2018/6/26.
 */
@FragmentScope
public class PursuePresenter extends BasePresenter<PursueContract.Model, PursueContract.View> {
    @Inject
    public PursuePresenter(PursueContract.Model model, PursueContract.View view) {
        super(model, view);
    }

    public void getPursueList(int page, int pageSize) {
        addSubscribe(mModel.getPursueList(page, pageSize)
                .compose(RxUtils.<PageResult<PursueBean>>getFlowableTransformer())
                .subscribeWith(new CommonSubscriber<PageResult<PursueBean>>(mView) {
                    @Override
                    public void onNext(PageResult<PursueBean> pursueBeanPageResult) {
                        mView.initPursueContent(pursueBeanPageResult.getData());
                    }
                }));
    }
}
