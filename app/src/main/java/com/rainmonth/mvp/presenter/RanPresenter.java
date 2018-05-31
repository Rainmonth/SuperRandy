package com.rainmonth.mvp.presenter;

import com.rainmonth.mvp.contract.RanContract;
import com.rainmonth.mvp.model.bean.RanContentBean;
import com.rainmonth.common.base.mvp.BasePresenter;

import javax.inject.Inject;

/**
 * Created by RandyZhang on 16/7/5.
 */
public class RanPresenter extends BasePresenter<RanContract.Model, RanContract.View> {

    @Inject
    public RanPresenter(RanContract.Model model, RanContract.View view) {
        super(model, view);
    }

    public void initialize() {
        mView.initViews(mModel.getRanContentList());
    }

    public void navToDetail(RanContentBean ranContentBean) {
        mView.navToDetail(ranContentBean);
    }
}
