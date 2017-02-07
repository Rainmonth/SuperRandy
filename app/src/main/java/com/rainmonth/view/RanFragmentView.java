package com.rainmonth.view;

import com.rainmonth.base.mvp.IBaseView;
import com.rainmonth.bean.RanContentBean;

import java.util.List;

/**
 * Created by RandyZhang on 16/7/5.
 */
public interface RanFragmentView extends IBaseView {

    void initViews(List<RanContentBean> ranContentBeanList);

    void navToDetail(RanContentBean ranContentBean);
}
