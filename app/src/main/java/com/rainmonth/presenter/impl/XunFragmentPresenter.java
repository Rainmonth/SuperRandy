package com.rainmonth.presenter.impl;

import android.content.Context;

import com.rainmonth.bean.XunNavigationInfo;
import com.rainmonth.model.XunFragmentModel;
import com.rainmonth.model.impl.XunFragmentModelImpl;
import com.rainmonth.base.mvp.BasePresenterImpl;
import com.rainmonth.view.XunFragmentView;

/**
 * Created by RandyZhang on 16/7/5.
 */
public class XunFragmentPresenter extends BasePresenterImpl<XunFragmentView, Object> {
    private Context context = null;
    private XunFragmentView xunFragmentView = null;
    private XunFragmentModel xunFragmentModel = null;

    public XunFragmentPresenter(Context context, XunFragmentView xunFragmentView) {
        super(xunFragmentView);
        if (null == xunFragmentView) {
            throw new IllegalArgumentException("View should not be null");
        }
        this.context = context;
        this.xunFragmentView = xunFragmentView;
        xunFragmentModel = new XunFragmentModelImpl();
    }

    public void initialize() {
        xunFragmentView.initViews(xunFragmentModel.getXunNavigationList());
    }

    public void navToDetail(XunNavigationInfo xunNavigationInfo) {
        xunFragmentView.navToDetail(xunNavigationInfo);
    }
}
