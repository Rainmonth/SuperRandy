package com.rainmonth.presenter.impl;

import android.content.Context;

import com.rainmonth.bean.RenContentInfo;
import com.rainmonth.model.RenFragmentModel;
import com.rainmonth.model.impl.RenFragmentModelImpl;
import com.rainmonth.base.mvp.BasePresenterImpl;
import com.rainmonth.view.RenFragmentView;

/**
 * Created by RandyZhang on 16/7/5.
 */
public class RenFragmentPresenter extends BasePresenterImpl<RenFragmentView, Object> {
    private Context context = null;
    private RenFragmentView renFragmentView = null;
    private RenFragmentModel renFragmentModel = null;

    public RenFragmentPresenter(Context context, RenFragmentView renFragmentView) {
        super(renFragmentView);
        if (null == renFragmentView) {
            throw new IllegalArgumentException("View should not be null");
        }
        this.context = context;
        this.renFragmentView = renFragmentView;
        renFragmentModel = new RenFragmentModelImpl();
    }

    public void initialize() {
        renFragmentView.initViews(renFragmentModel.getRenContentList());
    }

    public void navToDetail(RenContentInfo renContentInfo) {
        renFragmentView.navToDetail(renContentInfo);
    }
}
