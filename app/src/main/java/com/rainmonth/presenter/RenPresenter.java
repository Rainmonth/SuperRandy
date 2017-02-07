package com.rainmonth.presenter;

import com.google.gson.Gson;
import com.rainmonth.R;
import com.rainmonth.base.mvp.BasePresenter;
import com.rainmonth.base.mvp.BaseResponse;
import com.rainmonth.bean.BannerBean;
import com.rainmonth.bean.RenContentBean;
import com.rainmonth.model.IRenFragmentModel;
import com.rainmonth.model.RenFragmentModel;
import com.rainmonth.view.RenFragmentView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

/**
 * Created by RandyZhang on 16/7/5.
 */
public class RenPresenter extends BasePresenter<RenFragmentView, Response<BaseResponse>> implements IRenPresenter {
    private RenFragmentView renFragmentView = null;
    private IRenFragmentModel renFragmentModel = null;
    private String requestUrl = "";//用以区分不同接口，方便对不同接口做不同处理

    public RenPresenter(RenFragmentView renFragmentView) {
        super(renFragmentView);
        if (null == renFragmentView) {
            throw new IllegalArgumentException("View should not be null");
        }
        this.renFragmentView = renFragmentView;
        renFragmentModel = new RenFragmentModel();
    }

    @Override
    public void getContentList() {
        requestUrl = "getContentList";
//        renFragmentModel.getRenContentList(this);
        mSubscription = renFragmentModel.getRenContentList(this);
    }

    @Override
    public void getHomeBanner() {
        requestUrl = "Banner/getHomeBanner";
//        renFragmentModel.getHomeBannerList(this);
        mSubscription = renFragmentModel.getHomeBannerList(this);
    }

    @Override
    public void navToDetail(RenContentBean renContentBean) {
        renFragmentView.navToDetail(renContentBean);
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
        if ("Banner/getHomeBanner".equals(requestUrl)) {
//            List<BannerBean> bannerBeanList = (List<BannerBean>) data.body();
            List<BannerBean> bannerInfoList = new ArrayList<BannerBean>();
            /**
             * 1、如果有网络，则请求网络数据；
             * 2、没有网络，则读取本地缓存数据
             */
            String jsonStr = "{\"id\":\"10001\",\"type\":\"1\",\"title\":\"活动主页一\",\"url\":\"https://www.baidu.com\",\"banner_thumb_url\":\"http://pic2.cxtuku.com/00/02/31/b945758fd74d.jpg\"}";
            Gson gson = new Gson();
            for (int i = 0; i < 5; i++) {
                BannerBean bannerInfo = gson.fromJson(jsonStr, BannerBean.class);
                bannerInfoList.add(bannerInfo);
            }
            mView.initHomeBanners(bannerInfoList);
        }
        if ("getContentList".equals(requestUrl)) {
//            List<RenContentBean> renContentBeanList = (List<RenContentBean>) data.body();
            List<RenContentBean> renContentBeanList = new ArrayList<RenContentBean>();
            renContentBeanList.add(new RenContentBean(1, R.drawable.ren_bg_walk, "1", "旅行", "行走的力量"));
            renContentBeanList.add(new RenContentBean(2, R.drawable.ren_bg_sing, "2", "音乐", "音乐的力量"));
            renContentBeanList.add(new RenContentBean(3, R.drawable.ren_bg_sport, "3", "运动", "运动的力量"));
            renContentBeanList.add(new RenContentBean(4, R.drawable.ren_bg_read, "4", "阅读", "阅读的力量"));
            renContentBeanList.add(new RenContentBean(5, R.drawable.ren_bg_stay, "5", "坚持", "坚持的力量"));
            renContentBeanList.add(new RenContentBean(6, R.drawable.ren_bg_game, "6", "游戏", "游戏的力量"));
            renContentBeanList.add(new RenContentBean(7, R.drawable.ren_bg_share, "7", "分享", "分享的力量"));
            mView.initContentList(renContentBeanList);
        }
    }
}
