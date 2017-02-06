package com.rainmonth.presenter;

import com.rainmonth.base.mvp.BasePresenter;
import com.rainmonth.base.mvp.BaseResponse;
import com.rainmonth.bean.BannerBean;
import com.rainmonth.bean.RenContentInfo;
import com.rainmonth.model.IRenFragmentModel;
import com.rainmonth.model.RenFragmentModel;
import com.rainmonth.view.RenFragmentView;

import java.util.List;

import retrofit2.Response;

/**
 * Created by RandyZhang on 16/7/5.
 */
public class RenPresenter extends BasePresenter<RenFragmentView, Response<BaseResponse>> implements IRenPresenter {
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
    public List<RenContentInfo> getContentList() {
        return renFragmentModel.getRenContentList();
    }

    @Override
    public List<BannerBean> getHomeBanner() {

        return renFragmentModel.getHomeBanner(this);
    }

    @Override
    public void navToDetail(RenContentInfo renContentInfo) {
        renFragmentView.navToDetail(renContentInfo);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void beforeRequest() {
        super.beforeRequest();
        mView.toast("提示");
    }

    @Override
    public void requestError(String msg) {
        super.requestError(msg);
    }

    @Override
    public void requestComplete() {
        super.requestComplete();
    }

    @Override
    public void requestSuccess(Response<BaseResponse> data) {
        super.requestSuccess(data);
    }
}
