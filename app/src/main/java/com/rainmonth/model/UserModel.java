package com.rainmonth.model;

import com.rainmonth.base.mvp.BaseResponse;
import com.rainmonth.bean.UserBean;
import com.rainmonth.service.UserService;
import com.rainmonth.base.http.Api;
import com.rainmonth.base.mvp.BaseSchedulerTransformer;
import com.rainmonth.base.mvp.BaseSubscriber;
import com.rainmonth.base.http.RequestCallback;
import com.rainmonth.base.http.ServiceFactory;

import retrofit2.Response;
import rx.Subscription;

/**
 * IUserModel的实现
 * Created by RandyZhang on 16/9/19.
 */
public class UserModel implements IUserModel<Response<BaseResponse>> {
    @Override
    public Subscription login(RequestCallback<Response<BaseResponse>> callback, String username, String psw) {
        return ServiceFactory.createService(Api.baseUrl, UserService.class).login(username, psw)
                .compose(new BaseSchedulerTransformer<Response<BaseResponse>>())
                .subscribe(new BaseSubscriber<Response<BaseResponse>>(callback));
    }

    @Override
    public Subscription logout(RequestCallback<Response<BaseResponse>> callback) {
        return ServiceFactory.createService(Api.baseUrl, UserService.class).logout()
                .compose(new BaseSchedulerTransformer<Response<BaseResponse>>())
                .subscribe(new BaseSubscriber<Response<BaseResponse>>(callback));
    }

    @Override
    public Subscription register(RequestCallback<Response<BaseResponse>> callback, UserBean userBean) {
        return ServiceFactory.createService(Api.baseUrl, UserService.class).register(userBean)
                .compose(new BaseSchedulerTransformer<Response<BaseResponse>>())
                .subscribe(new BaseSubscriber<Response<BaseResponse>>(callback));
    }

    @Override
    public Subscription getUserInfo(RequestCallback<Response<BaseResponse>> callback, String id) {
        return ServiceFactory.createService(Api.baseUrl, UserService.class).getUserInfo(id)
                .compose(new BaseSchedulerTransformer<Response<BaseResponse>>())
                .subscribe(new BaseSubscriber<Response<BaseResponse>>(callback));
    }

    @Override
    public Subscription updateUserInfo(RequestCallback<Response<BaseResponse>> callback, UserBean userBean) {
        return ServiceFactory.createService(Api.baseUrl, UserService.class).updateUserInfo(userBean)
                .compose(new BaseSchedulerTransformer<Response<BaseResponse>>())
                .subscribe(new BaseSubscriber<Response<BaseResponse>>(callback));
    }
}
