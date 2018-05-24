package com.rainmonth.mvp.contract;

import com.rainmonth.common.base.mvp.IBaseModel;
import com.rainmonth.common.base.mvp.IBaseView;
import com.rainmonth.mvp.model.bean.XunNavigationBean;

import java.util.List;

/**
 * Created by RandyZhang on 2018/5/24.
 */

public interface XunContract {
    interface Model extends IBaseModel {

    }

    interface View extends IBaseView {
        void initViews(List<XunNavigationBean> xunNavigationBeanList);

        void navToDetail(XunNavigationBean xunNavigationBean);
    }
}
