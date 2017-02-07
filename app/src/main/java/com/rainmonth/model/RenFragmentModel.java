package com.rainmonth.model;

import com.rainmonth.base.mvp.BaseSchedulerTransformer;
import com.rainmonth.base.mvp.BaseSubscriber;
import com.rainmonth.bean.BannerBean;
import com.rainmonth.bean.RenContentBean;
import com.rainmonth.service.BannerService;
import com.rainmonth.utils.http.Api;
import com.rainmonth.utils.http.RequestCallback;
import com.rainmonth.utils.http.ServiceFactory;

import java.util.List;

import retrofit2.Response;
import rx.Subscription;

/**
 * Created by RandyZhang on 16/7/5.
 */
public class RenFragmentModel implements IRenFragmentModel {
    @Override
    public Subscription getRenContentList(RequestCallback callback) {
        return ServiceFactory.createService(Api.baseUrl, BannerService.class).getContentList()
                .compose(new BaseSchedulerTransformer<Response<List<RenContentBean>>>())
                .subscribe(new BaseSubscriber<>(callback));
    }

    @Override
    public Subscription getHomeBannerList(RequestCallback callback) {
        return ServiceFactory.createService(Api.baseUrl, BannerService.class).getHomeBannerList()
                .compose(new BaseSchedulerTransformer<Response<List<BannerBean>>>())
                .subscribe(new BaseSubscriber<>(callback));
    }
}
