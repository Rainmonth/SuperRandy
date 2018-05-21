package com.rainmonth.mvp.view;

import com.rainmonth.common.base.mvp.IBaseView;
import com.rainmonth.mvp.model.bean.RanContentBean;

import java.util.List;

/**
 * Created by RandyZhang on 16/7/5.
 */
public interface RanFragmentView extends IBaseView {

    void initViews(List<RanContentBean> ranContentBeanList);

    void navToDetail(RanContentBean ranContentBean);
}
