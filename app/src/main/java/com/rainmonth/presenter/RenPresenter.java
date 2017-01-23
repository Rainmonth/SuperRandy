package com.rainmonth.presenter;

import com.rainmonth.base.mvp.BasePresenter;
import com.rainmonth.bean.BannerBean;
import com.rainmonth.bean.RenContentInfo;
import com.rainmonth.model.IRenFragmentModel;
import com.rainmonth.model.RenFragmentModel;
import com.rainmonth.view.RenFragmentView;

import java.util.List;

/**
 * Created by RandyZhang on 16/7/5.
 */
public class RenPresenter extends BasePresenter<RenFragmentView, Object> implements IRenPresenter {
    private RenFragmentView renFragmentView = null;
    private IRenFragmentModel renFragmentModel = null;

    public RenPresenter(RenFragmentView renFragmentView) {
        super(renFragmentView);
        if (null == renFragmentView) {
            throw new IllegalArgumentException("View should not be null");
        }
        this.renFragmentView = renFragmentView;
        renFragmentModel = new RenFragmentModel();
    }


    @Override
    public void init() {
        renFragmentView.initViews(renFragmentModel.getRenContentList());
    }

    @Override
    public List<RenContentInfo> getContentList() {
        return renFragmentModel.getRenContentList();
    }

    @Override
    public List<BannerBean> getHomeBanner() {
        return renFragmentModel.getHomeBanner();
    }

    @Override
    public void navToDetail(RenContentInfo renContentInfo) {
        renFragmentView.navToDetail(renContentInfo);
    }
}
