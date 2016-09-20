package com.rainmonth.model.impl;

import com.rainmonth.base.mvp.BaseSchedulerTransformer;
import com.rainmonth.base.mvp.BaseSubscriber;
import com.rainmonth.bean.UserInfo;
import com.rainmonth.model.ILoginModel;
import com.rainmonth.model.IRegisterModel;
import com.rainmonth.service.UserService;
import com.rainmonth.utils.http.Api;
import com.rainmonth.utils.http.RequestCallback;
import com.rainmonth.utils.http.ServiceFactory;
import com.rainmonth.utils.http.UserLoginResponse;

import retrofit2.Response;
import rx.Subscription;

/**
 * Created by RandyZhang on 16/9/19.
 */
public class RegisterModelImpl implements IRegisterModel<Response<UserLoginResponse>> {

    @Override
    public Subscription register(RequestCallback<Response<UserLoginResponse>> callback, UserInfo userInfo) {
        return ServiceFactory.createService(Api.baseUrl, UserService.class)
                .registerRx(userInfo.getMobile(), userInfo.getUsername(), userInfo.getPsw(), userInfo.getEmail())
                .compose(new BaseSchedulerTransformer<Response<UserLoginResponse>>())
                .subscribe(new BaseSubscriber<Response<UserLoginResponse>>(callback));
    }
}
