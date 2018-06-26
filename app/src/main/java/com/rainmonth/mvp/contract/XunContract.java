package com.rainmonth.mvp.contract;

import com.rainmonth.common.base.mvp.IBaseModel;
import com.rainmonth.common.base.mvp.IBaseView;
import com.rainmonth.common.http.Result;
import com.rainmonth.mvp.model.bean.XunNavigationBean;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by RandyZhang on 2018/5/24.
 */

public interface XunContract {
    interface Model extends IBaseModel {
        Flowable<Result<List<XunNavigationBean>>> getNavigationBeanList();
    }

    interface View extends IBaseView {
        void initNavigationContent(List<XunNavigationBean> xunNavigationBeanList);

        void navToDetail(XunNavigationBean xunNavigationBean);
    }
}
