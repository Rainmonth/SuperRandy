package com.rainmonth.model;

import com.google.gson.Gson;
import com.rainmonth.R;
import com.rainmonth.base.mvp.BaseSchedulerTransformer;
import com.rainmonth.base.mvp.BaseSubscriber;
import com.rainmonth.bean.BannerBean;
import com.rainmonth.bean.RenContentInfo;
import com.rainmonth.service.BannerService;
import com.rainmonth.utils.http.Api;
import com.rainmonth.utils.http.RequestCallback;
import com.rainmonth.utils.http.ServiceFactory;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;
import rx.Subscription;

/**
 * Created by RandyZhang on 16/7/5.
 */
public class RenFragmentModel implements IRenFragmentModel {
    @Override
    public Subscription getRenContentList(RequestCallback callback) {
//        List<RenContentInfo> renContentInfoList = new ArrayList<RenContentInfo>();
//
//        renContentInfoList.add(new RenContentInfo(1, R.drawable.ren_bg_walk, "1", "旅行", "行走的力量"));
//        renContentInfoList.add(new RenContentInfo(2, R.drawable.ren_bg_sing, "2", "音乐", "音乐的力量"));
//        renContentInfoList.add(new RenContentInfo(3, R.drawable.ren_bg_sport, "3", "运动", "运动的力量"));
//        renContentInfoList.add(new RenContentInfo(4, R.drawable.ren_bg_read, "4", "阅读", "阅读的力量"));
//        renContentInfoList.add(new RenContentInfo(5, R.drawable.ren_bg_stay, "5", "坚持", "坚持的力量"));
//        renContentInfoList.add(new RenContentInfo(6, R.drawable.ren_bg_game, "6", "游戏", "游戏的力量"));
//        renContentInfoList.add(new RenContentInfo(7, R.drawable.ren_bg_share, "7", "分享", "分享的力量"));
//
//        return renContentInfoList;

        return ServiceFactory.createService(Api.baseUrl, BannerService.class).getContentList()
                .compose(new BaseSchedulerTransformer<Response<List<RenContentInfo>>>())
                .subscribe(new BaseSubscriber<>(callback));
    }

//    public Subscription login(RequestCallback<Response<BaseResponse>> callback, String username, String psw) {
//        return ServiceFactory.createService(Api.baseUrl, UserService.class).loginRx(username, psw)
//                .compose(new BaseSchedulerTransformer<Response<BaseResponse>>())
//                .subscribe(new BaseSubscriber<Response<BaseResponse>>(callback));
//    }

    @Override
    public Subscription getHomeBannerList(RequestCallback callback) {
        return ServiceFactory.createService(Api.baseUrl, BannerService.class).getHomeBannerList()
                .compose(new BaseSchedulerTransformer<Response<List<BannerBean>>>())
                .subscribe(new BaseSubscriber<>(callback));
    }
}
