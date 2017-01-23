package com.rainmonth.model;

import com.rainmonth.base.mvp.BaseSchedulerTransformer;
import com.rainmonth.base.mvp.BaseSubscriber;
import com.rainmonth.bean.UserBean;
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
public class RegisterModel implements IRegisterModel<Response<UserLoginResponse>> {

    @Override
    public Subscription register(RequestCallback<Response<UserLoginResponse>> callback, UserBean userBean) {
        return ServiceFactory.createService(Api.baseUrl, UserService.class)
                .registerRx(userBean.getMobile(), userBean.getUsername(), userBean.getPsw(), userBean.getEmail())
                .compose(new BaseSchedulerTransformer<Response<UserLoginResponse>>())
                .subscribe(new BaseSubscriber<>(callback));
    }
}
