package com.rainmonth.mvp.view;

import com.rainmonth.common.base.mvp.IBaseView;
import com.rainmonth.mvp.model.bean.XunNavigationBean;

import java.util.List;

/**
 * Created by RandyZhang on 16/7/5.
 */
public interface XunFragmentView extends IBaseView {

    void initViews(List<XunNavigationBean> xunNavigationBeanList);

    void navToDetail(XunNavigationBean xunNavigationBean);
}
