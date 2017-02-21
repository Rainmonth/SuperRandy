package com.rainmonth.model;

import com.rainmonth.base.mvp.BaseResponse;
import com.rainmonth.base.mvp.BaseSchedulerTransformer;
import com.rainmonth.base.mvp.BaseSubscriber;
import com.rainmonth.bean.UserBean;
import com.rainmonth.service.UserService;
import com.rainmonth.base.http.Api;
import com.rainmonth.base.http.RequestCallback;
import com.rainmonth.base.http.ServiceFactory;

import retrofit2.Response;
import rx.Subscription;

/**
 * Created by RandyZhang on 16/9/19.
 */
public class RegisterModel implements IRegisterModel<Response<BaseResponse>> {

    @Override
    public Subscription register(RequestCallback<Response<BaseResponse>> callback, UserBean userBean) {
        return ServiceFactory.createService(Api.baseUrl, UserService.class)
                .register(userBean)
                .compose(new BaseSchedulerTransformer<Response<BaseResponse>>())
                .subscribe(new BaseSubscriber<>(callback));
    }
}
