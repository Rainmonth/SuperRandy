package com.rainmonth.presenter;

import android.content.Context;

import com.rainmonth.bean.XunNavigationBean;
import com.rainmonth.base.mvp.BasePresenter;
import com.rainmonth.view.XunFragmentView;

/**
 * Created by RandyZhang on 16/7/5.
 */
public class XunPresenter extends BasePresenter<XunFragmentView, Object> {
    private Context context = null;
    private XunFragmentView xunFragmentView = null;
//    private IXunFragmentModel xunFragmentModel = null;

    public XunPresenter(Context context, XunFragmentView xunFragmentView) {
        super(xunFragmentView);
        if (null == xunFragmentView) {
            throw new IllegalArgumentException("View should not be null");
        }
        this.context = context;
        this.xunFragmentView = xunFragmentView;
//        xunFragmentModel = new XunFragmentModel();
    }

    public void initialize() {
//        xunFragmentView.initViews(xunFragmentModel.getXunNavigationList());
    }

    public void navToDetail(XunNavigationBean xunNavigationBean) {
        xunFragmentView.navToDetail(xunNavigationBean);
    }
}
