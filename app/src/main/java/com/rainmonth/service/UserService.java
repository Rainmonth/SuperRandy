package com.rainmonth.service;

import com.rainmonth.base.mvp.BaseResponse;
import com.rainmonth.bean.UserBean;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by RandyZhang on 16/9/19.
 */
public interface UserService {
    @FormUrlEncoded
    @POST("User/login")
    Call<BaseResponse> loginCall(@Field("username") String username, @Field("psw") String psw);

    @POST("User/register")
    Call<UserBean> registerCall(@Body UserBean userBean);

    @GET("User/getUserBean")
    Call<UserBean> getUserInfoCall(@Query("id") int id);

    @FormUrlEncoded
    @POST("api/User/login")
    Observable<Response<BaseResponse>> login(@Field("username") String username, @Field("psw") String psw);

    @FormUrlEncoded
    @POST("api/User/logout")
    Observable<Response<BaseResponse>> logout();

    @FormUrlEncoded
    @POST("api/User/register")
    Observable<Response<BaseResponse>> register(@Body UserBean userBean);

    @GET("api/User/getUserInfo")
    Observable<Response<BaseResponse>> getUserInfo(@Query("id") String id);

    @POST("api/User/updateUserInfo")
    Observable<Response<BaseResponse>> updateUserInfo(@Body UserBean userBean);
}
