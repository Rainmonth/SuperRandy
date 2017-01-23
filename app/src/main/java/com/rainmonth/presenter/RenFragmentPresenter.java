package com.rainmonth.presenter;

import com.rainmonth.base.mvp.BasePresenter;
import com.rainmonth.bean.RenContentInfo;
import com.rainmonth.model.IRenFragmentModel;
import com.rainmonth.model.RenFragmentModel;
import com.rainmonth.view.RenFragmentView;

/**
 * Created by RandyZhang on 16/7/5.
 */
public class RenFragmentPresenter extends BasePresenter<RenFragmentView, Object> {
    private RenFragmentView renFragmentView = null;
    private IRenFragmentModel renFragmentModel = null;

    public RenFragmentPresenter(RenFragmentView renFragmentView) {
        super(renFragmentView);
        if (null == renFragmentView) {
            throw new IllegalArgumentException("View should not be null");
        }
        this.renFragmentView = renFragmentView;
        renFragmentModel = new RenFragmentModel();
    }

    public void initialize() {
        renFragmentView.initViews(renFragmentModel.getRenContentList());
    }

    public void navToDetail(RenContentInfo renContentInfo) {
        renFragmentView.navToDetail(renContentInfo);
    }
}
