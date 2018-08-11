package com.rainmonth.image.api;

import com.rainmonth.image.mvp.model.bean.UserBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @desprition: Unsplash 用户信息API
 * @author: RandyZhang
 * @date: 2018/8/8 上午7:01
 */
public interface UUserApi {

    @Headers({
            "Accept-Version:v1",
            "Authorization:Client-ID ae1715b58d53e958f990d42c9a3e221120a292efd592d66d0ba3717ccc4c9abe"
    })
    @GET(Consts.GET_USER_INFO)
    Observable<UserBean> getUserInfo(@Path("username") String username,
                                     @Query("w") int w,
                                     @Query("h") int h);


    @Headers({
            "Accept-Version:v1",
            "Authorization:Client-ID ae1715b58d53e958f990d42c9a3e221120a292efd592d66d0ba3717ccc4c9abe"
    })
    @GET(Consts.GET_USER_LIKE_PHOTOS)
    Observable<UserBean> getUserLikePhotos(@Path("username") String username,
                                           @Query("page") int page,
                                           @Query("per_page") int perPage,
                                           @Query("order_by") String orderBy);

    @Headers({
            "Accept-Version:v1",
            "Authorization:Client-ID ae1715b58d53e958f990d42c9a3e221120a292efd592d66d0ba3717ccc4c9abe"
    })
    @GET(Consts.GET_USER_PERSONAL_SITE)
    Observable<String> getUserPersonalSite(@Path("username") String username);

    @Headers({
            "Accept-Version:v1",
            "Authorization:Client-ID ae1715b58d53e958f990d42c9a3e221120a292efd592d66d0ba3717ccc4c9abe"
    })
    @GET(Consts.GET_USER_COLLECTIONS)
    Observable<String> getUserCollections(@Path("username") String username);


}