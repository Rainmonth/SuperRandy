package com.rainmonth.presenter;

import android.content.Context;

import com.rainmonth.bean.RanContentInfo;
import com.rainmonth.model.RanFragmentModel;
import com.rainmonth.model.RanFragmentModelImpl;
import com.rainmonth.view.RanFragmentView;

/**
 * Created by RandyZhang on 16/7/5.
 */
public class RanFragmentPresenter implements BasePresenter {
    private Context context = null;
    private RanFragmentView ranFragmentView = null;
    private RanFragmentModel ranFragmentModel = null;

    public RanFragmentPresenter(Context context, RanFragmentView ranFragmentView) {
        if (null == ranFragmentView) {
            throw new IllegalArgumentException("View should not be null");
        }
        this.context = context;
        this.ranFragmentView = ranFragmentView;
        ranFragmentModel = new RanFragmentModelImpl();
    }

    @Override
    public void initialize() {
        ranFragmentView.initViews(ranFragmentModel.getRanContentList());
    }

    public void navToDetail(RanContentInfo ranContentInfo) {
        ranFragmentView.navToDetail(ranContentInfo);
    }
}
