package com.rainmonth.view;

import com.rainmonth.bean.XunNavigationInfo;
import com.rainmonth.base.mvp.BaseView;

import java.util.List;

/**
 * Created by RandyZhang on 16/7/5.
 */
public interface XunFragmentView extends BaseView {

    void initViews(List<XunNavigationInfo> xunNavigationInfoList);

    void navToDetail(XunNavigationInfo xunNavigationInfo);
}
