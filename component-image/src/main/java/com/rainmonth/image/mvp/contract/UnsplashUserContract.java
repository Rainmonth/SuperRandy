package com.rainmonth.image.mvp.contract;

import com.rainmonth.common.base.mvp.IBaseModel;
import com.rainmonth.common.base.mvp.IBaseView;
import com.rainmonth.common.http.BaseResponse;
import com.rainmonth.image.mvp.model.bean.UserBean;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.Response;

/**
 * @desprition: Unsplash 用户Contract
 * @author: RandyZhang
 * @date: 2018/8/8 上午7:34
 */
public interface UnsplashUserContract {
    interface Model extends IBaseModel {
        Observable<UserBean> getUserInfo(String username, int w, int h);
    }

    interface View extends IBaseView {
        void initUserInfo(UserBean responseResponse);
    }
}