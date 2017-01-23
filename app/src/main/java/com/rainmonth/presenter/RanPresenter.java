package com.rainmonth.presenter;

import com.rainmonth.bean.RanContentInfo;
import com.rainmonth.model.IRanFragmentModel;
import com.rainmonth.model.RanFragmentModel;
import com.rainmonth.base.mvp.BasePresenter;
import com.rainmonth.view.RanFragmentView;

/**
 * Created by RandyZhang on 16/7/5.
 */
public class RanPresenter extends BasePresenter<RanFragmentView, Object> {
    private IRanFragmentModel ranFragmentModel = null;

    public RanPresenter(RanFragmentView ranFragmentView) {
        super(ranFragmentView);
        if (null == ranFragmentView) {
            throw new IllegalArgumentException("View should not be null");
        }
        ranFragmentModel = new RanFragmentModel();
    }

    public void initialize() {
        mView.initViews(ranFragmentModel.getRanContentList());
    }

    public void navToDetail(RanContentInfo ranContentInfo) {
        mView.navToDetail(ranContentInfo);
    }
}
