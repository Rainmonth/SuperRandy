package com.rainmonth.image.api;

import com.rainmonth.common.http.BaseResponse;

import io.reactivex.Flowable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @desprition: Unsplash 用户信息API
 * @author: RandyZhang
 * @date: 2018/8/8 上午7:01
 */
public interface UnsplashUserService {

    @Headers({
            "Accept-Version:v1",
            "Authorization:Client-ID ae1715b58d53e958f990d42c9a3e221120a292efd592d66d0ba3717ccc4c9abe"
    })
    @GET("https://api.unsplash.com/users/{username}")
    Flowable<Response<BaseResponse>> getUserInfo(@Path("username") String username,
                                                 @Query("w") int w,
                                                 @Query("h") int h);


}