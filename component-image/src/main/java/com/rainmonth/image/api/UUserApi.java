package com.rainmonth.image.api;

import com.rainmonth.image.mvp.model.bean.CollectionBean;
import com.rainmonth.image.mvp.model.bean.UserBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
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
    Observable<List<CollectionBean>> getUserCollections(@Path("username") String username,
                                                        @Query("page") int page,
                                                        @Query("per_page") int perPage);


    @Headers({
            "Accept-Version:v1",
            "Authorization:Client-ID ae1715b58d53e958f990d42c9a3e221120a292efd592d66d0ba3717ccc4c9abe"
    })
    @GET(Consts.GET_USER_PHOTOS)
    Observable<List<CollectionBean>> getUserPhotos(@Path("username") String username,
                                                   @Query("page") int page,
                                                   @Query("per_page") int perPage,
                                                   @Query("order_by") String oderBy,
                                                   @Query("stats") boolean stats,
                                                   @Query("resolution") String resolutions,
                                                   @Query("quantity") int quantity);

    /**
     * 获取用户统计数据（默认30天）
     *
     * @param username    用户名
     * @param resolutions 单位（days、months）
     * @param quantity    数量 30
     * @return
     */
    @Headers({
            "Accept-Version:v1",
            "Authorization:Client-ID ae1715b58d53e958f990d42c9a3e221120a292efd592d66d0ba3717ccc4c9abe"
    })
    @GET(Consts.GET_USER_STATISTICS)
    Observable<String> getUserStatistics(@Path("username") String username,
                                         @Query("resolution") String resolutions,
                                         @Query("quantity") int quantity);

    /**
     * 获取当前用户信息
     * todo 修改对应的实体
     *
     * @return 当前用户信息（与合集中的useInfo不一样）
     */
    @Headers({
            "Accept-Version:v1",
            "Authorization:Client-ID ae1715b58d53e958f990d42c9a3e221120a292efd592d66d0ba3717ccc4c9abe"
    })
    @GET(Consts.GET_CURRENT_USER_INFO)
    Observable<UserBean> getCurrentUserInfo();

    /**
     * 修改当前用户信息
     */
    @Headers({
            "Accept-Version:v1",
            "Authorization:Client-ID ae1715b58d53e958f990d42c9a3e221120a292efd592d66d0ba3717ccc4c9abe"
    })
    @FormUrlEncoded
    @PUT(Consts.UPDATE_CURRENT_USER_INFO)
    Observable<UserBean> updateCurrentUserInfo(String username);

}