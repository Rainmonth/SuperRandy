package com.rainmonth.presenter.impl;

import com.rainmonth.bean.RanContentInfo;
import com.rainmonth.model.RanFragmentModel;
import com.rainmonth.model.impl.RanFragmentModelImpl;
import com.rainmonth.base.mvp.BasePresenterImpl;
import com.rainmonth.view.RanFragmentView;

/**
 * Created by RandyZhang on 16/7/5.
 */
public class RanFragmentPresenter extends BasePresenterImpl<RanFragmentView, Object> {
    private RanFragmentModel ranFragmentModel = null;

    public RanFragmentPresenter(RanFragmentView ranFragmentView) {
        super(ranFragmentView);
        if (null == ranFragmentView) {
            throw new IllegalArgumentException("View should not be null");
        }
        ranFragmentModel = new RanFragmentModelImpl();
    }

    public void navToDetail(RanContentInfo ranContentInfo) {
        mView.navToDetail(ranContentInfo);
    }
}
