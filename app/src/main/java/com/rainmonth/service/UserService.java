package com.rainmonth.service;

import com.rainmonth.base.mvp.BaseResponse;
import com.rainmonth.bean.UserBean;
import com.rainmonth.utils.http.UserLoginResponse;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by RandyZhang on 16/9/19.
 */
public interface UserService {
    @FormUrlEncoded
    @POST("User/login")
    Call<UserLoginResponse> loginCall(@Field("username") String username, @Field("psw") String psw);

    @POST("User/register")
    Call<UserBean> registerCall(@Body UserBean userBean);

    @GET("User/getUserBean")
    Call<UserBean> getUserInfoCall(@Query("id") int id);

    @FormUrlEncoded
    @POST("User/login")
    Observable<Response<BaseResponse>> login(@Field("username") String username, @Field("psw") String psw);

    @FormUrlEncoded
    @POST("User/logout")
    Observable<Response<BaseResponse>> logout();

    @FormUrlEncoded
    @POST("User/register")
    Observable<Response<BaseResponse>> register(@Body UserBean userBean);

    @GET("User/getUserInfo")
    Observable<Response<BaseResponse>> getUserInfo(@Query("id") String id);

    @POST("User/updateUserInfo")
    Observable<Response<BaseResponse>> updateUserInfo(@Body UserBean userBean);
}
