package com.rainmonth.mvp.contract;

import com.rainmonth.common.base.mvp.IBaseModel;
import com.rainmonth.common.base.mvp.IBaseView;
import com.rainmonth.common.http.PageData;
import com.rainmonth.common.http.PageResult;
import com.rainmonth.mvp.model.bean.PursueBean;

import io.reactivex.Flowable;

/**
 * @desprition: 追 Contract
 * @author: RandyZhang
 * @date: 2018/6/26 下午3:04
 */
public interface PursueContract {
    interface View extends IBaseView {
        void initPursueContent(PageData<PursueBean> pursueBeanPageData);

        void navToDetail(PursueBean pursueBean);
    }

    interface Model extends IBaseModel {
        Flowable<PageResult<PursueBean>> getPursueList(int page, int pageSize);
    }
}