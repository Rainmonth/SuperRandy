package com.rainmonth.view;

import com.rainmonth.bean.RanContentInfo;
import com.rainmonth.base.mvp.BaseView;

import java.util.List;

/**
 * Created by RandyZhang on 16/7/5.
 */
public interface RanFragmentView extends BaseView{

    void initViews(List<RanContentInfo> ranContentInfoList);

    void navToDetail(RanContentInfo ranContentInfo);
}
