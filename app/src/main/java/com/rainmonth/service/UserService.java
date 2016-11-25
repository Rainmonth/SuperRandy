package com.rainmonth.service;

import com.rainmonth.base.mvp.BaseResponse;
import com.rainmonth.bean.UserInfo;
import com.rainmonth.bean.UserLoginBean;
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
    @POST("User/register")
    Observable<Response<UserLoginResponse>> registerRx(@Field("mobile") String mobile, @Field("username") String username,
                                                       @Field("psw") String psw, @Field("email") String email);

    @GET("User/getUserInfo")
    Observable<Response<UserLoginResponse>> getUserInfoRx(@Query("id") int id);

    @FormUrlEncoded
    @POST("Mobile2/ShareBonus/login")
    Call<BaseResponse<UserLoginBean>> loginRx(@Field("mobile") String mobile,
                                @Field("password") String password,
                                @Field("code") String code);
    @GET("Mobile2/Auth/logout")
    Call<BaseResponse<Object>> logoutRx();

    @POST("Mobile2/Escrow/hasEscrowed")
    Call<BaseResponse<Object>> isEsc();

    @POST("Mobile2/User/account")
    Call<BaseResponse<Object>> getAccountInfo();
}
