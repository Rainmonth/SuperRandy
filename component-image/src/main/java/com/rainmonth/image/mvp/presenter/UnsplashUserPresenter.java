package com.rainmonth.image.mvp.presenter;

import com.rainmonth.common.base.mvp.BasePresenter;
import com.rainmonth.common.di.scope.ActivityScope;
import com.rainmonth.common.http.BaseResponse;
import com.rainmonth.common.http.CommonSubscriber;
import com.rainmonth.common.utils.RxUtils;
import com.rainmonth.image.mvp.contract.UnsplashUserContract;

import javax.inject.Inject;

import retrofit2.Response;

/**
 * @desprition: UnsplashUserPresenter
 * @author: RandyZhang
 * @date: 2018/8/8 上午7:54
 */
@ActivityScope
public class UnsplashUserPresenter extends BasePresenter<UnsplashUserContract.Model, UnsplashUserContract.View> {
    @Inject
    public UnsplashUserPresenter(UnsplashUserContract.Model model, UnsplashUserContract.View view) {
        super(model, view);
    }

    public void getUserInfo(int w, int h, String username) {
        addSubscribe(mModel.getUserInfo(w, h, username)
        .compose(RxUtils.<Response<BaseResponse>>getFlowableTransformer())
        .subscribeWith(new CommonSubscriber<Response<BaseResponse>>(mView) {
            @Override
            public void onNext(Response<BaseResponse> baseResponseResponse) {
                mView.initUserInfo(baseResponseResponse);
            }
        }));
    }
}