package com.rainmonth.view;

import com.rainmonth.bean.XunNavigationInfo;

import java.util.List;

/**
 * Created by RandyZhang on 16/7/5.
 */
public interface XunFragmentView {

    void initViews(List<XunNavigationInfo> xunNavigationInfoList);

    void navToDetail(int type, XunNavigationInfo xunNavigationInfo);
}
