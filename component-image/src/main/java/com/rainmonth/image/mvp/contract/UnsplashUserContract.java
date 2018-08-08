package com.rainmonth.image.mvp.contract;

import com.rainmonth.common.base.mvp.IBaseModel;
import com.rainmonth.common.base.mvp.IBaseView;
import com.rainmonth.common.http.BaseResponse;

import io.reactivex.Flowable;
import retrofit2.Response;

/**
 * @desprition: Unsplash 用户Contract
 * @author: RandyZhang
 * @date: 2018/8/8 上午7:34
 */
public interface UnsplashUserContract {
    interface Model extends IBaseModel {
        Flowable<Response<BaseResponse>> getUserInfo(int w, int h, String username);
    }

    interface View extends IBaseView {
        void initUserInfo(Response<BaseResponse> responseResponse);
    }
}