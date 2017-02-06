package com.rainmonth.service;

import com.rainmonth.bean.BannerBean;
import com.rainmonth.bean.RenContentInfo;
import com.rainmonth.utils.http.UserLoginResponse;

import java.util.List;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by RandyZhang on 16/9/19.
 */
public interface BannerService {

    @GET("User/getUserBean")
    Observable<Response<UserLoginResponse>> getUserInfoRx(@Query("id") int id);

    @GET("Banner/getHomeBanner")
    Observable<Response<List<BannerBean>>> getHomeBannerList();

    @GET("Content/getContentList")
    Observable<Response<List<RenContentInfo>>> getContentList();
}
