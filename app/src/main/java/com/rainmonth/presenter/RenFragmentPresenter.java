package com.rainmonth.presenter;

import android.content.Context;

import com.rainmonth.bean.RenContentInfo;
import com.rainmonth.bean.XunNavigationInfo;
import com.rainmonth.model.RenFragmentModel;
import com.rainmonth.model.RenFragmentModelImpl;
import com.rainmonth.model.XunFragmentModel;
import com.rainmonth.model.XunFragmentModelImpl;
import com.rainmonth.utils.http.BasePresenterImpl;
import com.rainmonth.view.RenFragmentView;
import com.rainmonth.view.XunFragmentView;

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
