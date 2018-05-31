package com.rainmonth.mvp.contract;

import com.rainmonth.common.base.mvp.IBaseModel;
import com.rainmonth.common.base.mvp.IBaseView;
import com.rainmonth.mvp.model.bean.RanContentBean;

import java.util.List;

/**
 * Created by RandyZhang on 2018/5/24.
 */

public interface RanContract {
    interface Model extends IBaseModel {
        public List<RanContentBean> getRanContentList();
    }

    interface View extends IBaseView {
        void initViews(List<RanContentBean> ranContentBeanList);

        void navToDetail(RanContentBean ranContentBean);
    }
}
