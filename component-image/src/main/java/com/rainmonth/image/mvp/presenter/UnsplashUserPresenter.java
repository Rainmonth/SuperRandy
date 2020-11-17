package com.rainmonth.image.mvp.presenter;

import com.rainmonth.common.base.mvp.BasePresenter;
import com.rainmonth.common.di.scope.ActivityScope;
import com.rainmonth.common.http.BaseResponse;
import com.rainmonth.common.http.CommonSubscriber;
import com.rainmonth.common.utils.RxUtils;
import com.rainmonth.image.mvp.contract.UnsplashUserContract;
import com.rainmonth.image.mvp.model.bean.UserBean;
import com.rainmonth.common.utils.log.LogUtils;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
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

    public void getUserInfo(String username, int w, int h) {
        addSubscribe(mModel.getUserInfo(username, w, h)
                .compose(RxUtils.<UserBean>getObservableTransformer())
                .subscribeWith(new CommonSubscriber<UserBean>(mView) {

                    @Override
                    public void onNext(UserBean userBean) {
                        mView.initUserInfo(userBean);
                    }
                }));
    }
}