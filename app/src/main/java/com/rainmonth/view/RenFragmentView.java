package com.rainmonth.view;

import com.rainmonth.base.mvp.IBaseView;
import com.rainmonth.bean.RenContentInfo;

import java.util.List;

/**
 * Created by RandyZhang on 16/7/5.
 */
public interface RenFragmentView extends IBaseView {

    void initViews(List<RenContentInfo> renContentInfoList);

    void navToDetail(RenContentInfo renContentInfo);
}
