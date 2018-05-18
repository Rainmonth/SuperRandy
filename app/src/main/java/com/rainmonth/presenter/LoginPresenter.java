package com.rainmonth.presenter;

import com.rainmonth.base.mvp.BaseResponse;
import com.rainmonth.bean.UserBean;
import com.rainmonth.base.mvp.BasePresenter;
import com.rainmonth.view.ILoginView;

import retrofit2.Response;

/**
 * Created by RandyZhang on 16/9/19.
 */
public class LoginPresenter extends BasePresenter<ILoginView, Response<BaseResponse>>
        implements ILoginPresenter {

//    private IUserModel<Response<BaseResponse>> mUserModel;

    public LoginPresenter(ILoginView mView) {
        super(mView);
//        mUserModel = new UserModel();
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
    public void requestSuccess(Response<BaseResponse> data) {
        super.requestSuccess(data);
        mView.naveToAfterLogin(data.body());
        UserBean userBean = (UserBean) data.body().getData();
    }

    @Override
    public void login(String username, String psw) {
//        mSubscription = mUserModel.login(this, username, psw);
    }
}
