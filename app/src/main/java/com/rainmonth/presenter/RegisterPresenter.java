package com.rainmonth.presenter;

import com.rainmonth.base.mvp.BasePresenter;
import com.rainmonth.base.mvp.BaseResponse;
import com.rainmonth.bean.UserBean;
import com.rainmonth.view.IRegisterView;

import retrofit2.Response;

/**
 * Created by RandyZhang on 16/9/19.
 */
public class RegisterPresenter extends BasePresenter<IRegisterView, Response<BaseResponse>>
        implements IRegisterPresenter {

//    private IRegisterModel<Response<BaseResponse>> mUserModel;

    public RegisterPresenter(IRegisterView mView) {
        super(mView);
//        mUserModel = new RegisterModel();
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
        mView.naveToAfterRegister(data.body());
    }

    @Override
    public void register(UserBean userBean) {
//        mSubscription = mUserModel.register(this, userBean);
    }
}
