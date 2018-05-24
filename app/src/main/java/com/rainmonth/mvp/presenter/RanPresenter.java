package com.rainmonth.mvp.presenter;

import com.rainmonth.mvp.contract.RanContract;
import com.rainmonth.mvp.model.bean.RanContentBean;
import com.rainmonth.common.base.mvp.BasePresenter;

/**
 * Created by RandyZhang on 16/7/5.
 */
public class RanPresenter extends BasePresenter<RanContract.Model, RanContract.View> {
//    private IRanFragmentModel ranFragmentModel = null;

    public RanPresenter() {
        super();
    }

    public void initialize() {
//        mView.initViews(ranFragmentModel.getRanContentList());
    }

    public void navToDetail(RanContentBean ranContentBean) {
//        mView.navToDetail(ranContentBean);
    }
}
