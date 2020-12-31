package com.rainmonth.mvp.presenter;

import com.rainmonth.common.base.mvp.BasePresenter;
import com.rainmonth.common.http.CommonSubscriber;
import com.rainmonth.common.http.Result;
import com.rainmonth.utils.RxUtils;
import com.rainmonth.mvp.contract.XunContract;
import com.rainmonth.mvp.model.bean.XunNavigationBean;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by RandyZhang on 16/7/5.
 */
public class XunPresenter extends BasePresenter<XunContract.Model, XunContract.View> {
    @Inject
    public XunPresenter(XunContract.Model model, XunContract.View view) {
        super(model, view);
    }

    public void getNavigationList() {
        addSubscribe(mModel.getNavigationBeanList()
                .compose(RxUtils.<Result<List<XunNavigationBean>>>getFlowableTransformer())
                .subscribeWith(new CommonSubscriber<Result<List<XunNavigationBean>>>(mView) {
                    @Override
                    public void onNext(Result<List<XunNavigationBean>> listResult) {
                        if (listResult.isSuccess()) {
                            mView.initNavigationContent(listResult.getData());
                        } else {
                            mView.toast(listResult.getMessage());
                        }
                    }
                }));

    }

    public void navToDetail(XunNavigationBean xunNavigationBean) {
        mView.navToDetail(xunNavigationBean);
    }
}
