package com.rainmonth.mvp.presenter;

import com.rainmonth.mvp.model.bean.RanContentBean;
import com.rainmonth.common.base.mvp.BasePresenter;
import com.rainmonth.mvp.view.RanFragmentView;

/**
 * Created by RandyZhang on 16/7/5.
 */
public class RanPresenter extends BasePresenter<RanFragmentView, Object> {
//    private IRanFragmentModel ranFragmentModel = null;

    public RanPresenter(RanFragmentView ranFragmentView) {
        super(ranFragmentView);
        if (null == ranFragmentView) {
            throw new IllegalArgumentException("View should not be null");
        }
//        ranFragmentModel = new RanFragmentModel();
    }

    public void initialize() {
//        mView.initViews(ranFragmentModel.getRanContentList());
    }

    public void navToDetail(RanContentBean ranContentBean) {
        mView.navToDetail(ranContentBean);
    }
}
