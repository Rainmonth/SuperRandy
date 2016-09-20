package com.rainmonth.presenter.impl;

import com.rainmonth.base.mvp.BasePresenterImpl;
import com.rainmonth.bean.UserInfo;
import com.rainmonth.model.ILoginModel;
import com.rainmonth.model.IRegisterModel;
import com.rainmonth.model.impl.LoginModelImpl;
import com.rainmonth.model.impl.RegisterModelImpl;
import com.rainmonth.presenter.ILoginPresenter;
import com.rainmonth.presenter.IRegisterPresenter;
import com.rainmonth.utils.http.UserLoginResponse;
import com.rainmonth.view.IRegisterView;

import retrofit2.Response;

/**
 * Created by RandyZhang on 16/9/19.
 */
public class RegisterPresenterImpl extends BasePresenterImpl<IRegisterView, Response<UserLoginResponse>>
        implements IRegisterPresenter {

    private IRegisterModel<Response<UserLoginResponse>> mUserModel;

    public RegisterPresenterImpl(IRegisterView mView) {
        super(mView);
        mUserModel = new RegisterModelImpl();
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void beforeRequest() {
        super.beforeRequest();
    }

    @Override
    public void requestError(String msg) {
        super.requestError(msg);
        mView.toast(msg);
    }

    @Override
    public void requestComplete() {
        super.requestComplete();
    }

    @Override
    public void requestSuccess(Response<UserLoginResponse> data) {
        super.requestSuccess(data);
        mView.naveToAfterRegister(data.body());
    }

    @Override
    public void register(UserInfo userInfo) {
        mSubscription = mUserModel.register(this, userInfo);
    }
}
