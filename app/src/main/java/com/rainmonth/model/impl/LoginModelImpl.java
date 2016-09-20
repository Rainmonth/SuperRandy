package com.rainmonth.model.impl;

import com.rainmonth.model.ILoginModel;
import com.rainmonth.service.UserService;
import com.rainmonth.utils.http.Api;
import com.rainmonth.base.mvp.BaseSchedulerTransformer;
import com.rainmonth.base.mvp.BaseSubscriber;
import com.rainmonth.utils.http.RequestCallback;
import com.rainmonth.utils.http.ServiceFactory;
import com.rainmonth.utils.http.UserLoginResponse;

import retrofit2.Response;
import rx.Subscription;

/**
 * Created by RandyZhang on 16/9/19.
 */
public class LoginModelImpl implements ILoginModel<Response<UserLoginResponse>> {
    @Override
    public Subscription login(RequestCallback<Response<UserLoginResponse>> callback, String username, String psw) {
        return ServiceFactory.createService(Api.baseUrl, UserService.class).loginRx(username, psw)
                .compose(new BaseSchedulerTransformer<Response<UserLoginResponse>>())
                .subscribe(new BaseSubscriber<Response<UserLoginResponse>>(callback));
    }

//    @Override
//    public Subscription register(RequestCallback<Response<UserLoginResponse>> callback, UserInfo userInfo) {
//        return ServiceFactory.createService(Api.baseUrl, UserService.class).registerRx(userInfo)
//                .compose(new BaseSchedulerTransformer<Response<UserLoginResponse>>())
//                .subscribe(new BaseSubscriber<Response<UserLoginResponse>>(callback));
//    }
//
//    @Override
//    public Subscription getUserInfo(RequestCallback<Response<UserLoginResponse>> callback, int id) {
//        return ServiceFactory.createService(Api.baseUrl, UserService.class).getUserInfoRx(id)
//                .compose(new BaseSchedulerTransformer<Response<UserLoginResponse>>())
//                .subscribe(new BaseSubscriber<Response<UserLoginResponse>>(callback));
//    }
}
