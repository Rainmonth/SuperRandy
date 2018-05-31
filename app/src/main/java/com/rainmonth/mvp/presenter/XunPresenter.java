package com.rainmonth.mvp.presenter;

import com.rainmonth.common.base.mvp.BasePresenter;
import com.rainmonth.mvp.contract.XunContract;
import com.rainmonth.mvp.model.bean.XunNavigationBean;

import javax.inject.Inject;

/**
 *
 * Created by RandyZhang on 16/7/5.
 */
public class XunPresenter extends BasePresenter<XunContract.Model, XunContract.View> {
    @Inject
    public XunPresenter(XunContract.Model model, XunContract.View view) {
        super(model, view);
    }

    public void initialize() {
        mView.initViews(mModel.getNavigationBeanList());
    }

    public void navToDetail(XunNavigationBean xunNavigationBean) {
        mView.navToDetail(xunNavigationBean);
    }
}
