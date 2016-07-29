package com.rainmonth.presenter;

import android.content.Context;

import com.rainmonth.bean.XunNavigationInfo;
import com.rainmonth.model.XunFragmentModel;
import com.rainmonth.model.XunFragmentModelImpl;
import com.rainmonth.view.XunFragmentView;

/**
 * Created by RandyZhang on 16/7/5.
 */
public class XunFragmentPresenter implements BasePresenter {
    private Context context = null;
    private XunFragmentView xunFragmentView = null;
    private XunFragmentModel xunFragmentModel = null;

    public XunFragmentPresenter(Context context, XunFragmentView xunFragmentView) {
        if (null == xunFragmentView) {
            throw new IllegalArgumentException("View should not be null");
        }
        this.context = context;
        this.xunFragmentView = xunFragmentView;
        xunFragmentModel = new XunFragmentModelImpl();
    }

    @Override
    public void initialize() {
        xunFragmentView.initViews(xunFragmentModel.getXunNavigationList());
    }

    public void navToDetail(int type, XunNavigationInfo xunNavigationInfo) {
        xunFragmentView.navToDetail(type, xunNavigationInfo);
    }
}
