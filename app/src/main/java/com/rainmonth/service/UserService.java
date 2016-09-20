package com.rainmonth.service;

import com.rainmonth.bean.UserInfo;
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
    Call<UserLoginResponse> login(@Field("username") String username, @Field("psw") String psw);

    @POST("User/register")
    Call<UserInfo> register(@Body UserInfo userInfo);

    @GET("User/getUserInfo")
    Call<UserInfo> getUserInfo(@Query("id") int id);

    @FormUrlEncoded
    @POST("User/login")
    Observable<Response<UserLoginResponse>> loginRx(@Field("username") String username, @Field("psw") String psw);

    @FormUrlEncoded
    @POST("User/Register")
    Observable<Response<UserLoginResponse>> registerRx(@Body UserInfo userInfo);

    @GET("User/getUserInfo")
    Observable<Response<UserLoginResponse>> getUserInfoRx(@Query("id") int id);
}
