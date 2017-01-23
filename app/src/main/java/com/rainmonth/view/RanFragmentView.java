package com.rainmonth.view;

import com.rainmonth.base.mvp.IBaseView;
import com.rainmonth.bean.RanContentInfo;

import java.util.List;

/**
 * Created by RandyZhang on 16/7/5.
 */
public interface RanFragmentView extends IBaseView {

    void initViews(List<RanContentInfo> ranContentInfoList);

    void navToDetail(RanContentInfo ranContentInfo);
}
