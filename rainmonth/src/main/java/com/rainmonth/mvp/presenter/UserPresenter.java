package com.rainmonth.mvp.presenter;

import com.rainmonth.common.di.scope.ActivityScope;
import com.rainmonth.common.http.CommonSubscriber;
import com.rainmonth.common.http.Result;
import com.rainmonth.utils.RxUtils;
import com.rainmonth.mvp.contract.UserContract;
import com.rainmonth.common.base.mvp.BasePresenter;

import javax.inject.Inject;

/**
 * Created by RandyZhang on 16/9/19.
 */
@ActivityScope
public class UserPresenter extends BasePresenter<UserContract.Model, UserContract.View> {

    @Inject
    public UserPresenter(UserContract.Model model, UserContract.View view) {
        super(model, view);
    }

    public void login(String username, String password) {
        addSubscribe(mModel.login(username, password)
                .compose(RxUtils.<Result>getFlowableTransformer())
                .subscribeWith(new CommonSubscriber<Result>(mView) {

                    @Override
                    public void onNext(Result result) {
                        mView.naveToAfterLogin(result);
                    }
                }));
    }
}
