package com.rainmonth.view;

import com.rainmonth.bean.RenContentInfo;

import java.util.List;

/**
 * Created by RandyZhang on 16/7/5.
 */
public interface RenFragmentView {

    void initViews(List<RenContentInfo> renContentInfoList);

    void navToDetail(RenContentInfo renContentInfo);
}
