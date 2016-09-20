package com.rainmonth.presenter;

import com.rainmonth.model.ILoginModel;
import com.rainmonth.model.LoginModelImpl;
import com.rainmonth.base.mvp.BasePresenterImpl;
import com.rainmonth.utils.http.UserLoginResponse;
import com.rainmonth.view.ILoginView;

import retrofit2.Response;

/**
 * Created by RandyZhang on 16/9/19.
 */
public class LoginPresenterImpl extends BasePresenterImpl<ILoginView, Response<UserLoginResponse>>
        implements ILoginPresenter {

    private ILoginModel<Response<UserLoginResponse>> mUserModel;

    public LoginPresenterImpl(ILoginView mView) {
        super(mView);
        mUserModel = new LoginModelImpl();
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
        mView.naveToAfterLogin(data.body());
    }

    @Override
    public void login(String username, String psw) {
        mSubscription = mUserModel.login(this, username, psw);
    }
}
