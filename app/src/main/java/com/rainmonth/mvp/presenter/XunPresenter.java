package com.rainmonth.mvp.presenter;

import android.content.Context;

import com.rainmonth.common.base.mvp.BasePresenter;
import com.rainmonth.mvp.contract.XunContract;
import com.rainmonth.mvp.model.bean.XunNavigationBean;

/**
 * Created by RandyZhang on 16/7/5.
 */
public class XunPresenter extends BasePresenter<XunContract.Model, XunContract.View> {
    private Context context = null;
//    private IXunFragmentModel xunFragmentModel = null;

    public XunPresenter(Context context) {
    }

    public void initialize() {
//        xunFragmentView.initViews(xunFragmentModel.getXunNavigationList());
    }

    public void navToDetail(XunNavigationBean xunNavigationBean) {
//        xunFragmentView.navToDetail(xunNavigationBean);
    }
}
