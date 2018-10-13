package com.rainmonth.image.api;

import com.rainmonth.image.mvp.model.bean.CollectionBean;
import com.rainmonth.image.mvp.model.bean.PhotoBean;
import com.rainmonth.image.mvp.model.bean.SiteBean;
import com.rainmonth.image.mvp.model.bean.UserBean;

import java.util.List;

import io.reactivex.Observable;
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

    /**
     * 获取某个用户信息
     *
     * @param username 用户名
     * @param w        用户头像的宽度
     * @param h        用户头像的高度
     * @return 用户信息
     */
    @Headers({
            Consts.HEADER_VERSION,
            Consts.HEADER_BEARER_AUTHORIZATION
    })
    @GET(Consts.GET_USER_INFO)
    Observable<UserBean> getUserInfo(@Path("username") String username,
                                     @Query("w") int w,
                                     @Query("h") int h);


    /**
     * @param username 用户名
     * @param page     页码
     * @param perPage  页长
     * @param orderBy  图片排序方式latest, oldest, popular; default: latest
     * @return 图片列表
     */
    @Headers({
            Consts.HEADER_VERSION,
            Consts.HEADER_BEARER_AUTHORIZATION
    })
    @GET(Consts.GET_USER_LIKE_PHOTOS)
    Observable<List<PhotoBean>> getUserLikePhotos(@Path("username") String username,
                                                  @Query("page") int page,
                                                  @Query("per_page") int perPage,
                                                  @Query("order_by") String orderBy);

    /**
     * 获取用户个人网站
     *
     * @param username 用户名
     * @return 个人网站地址
     */
    @Headers({
            Consts.HEADER_VERSION,
            Consts.HEADER_BEARER_AUTHORIZATION
    })
    @GET(Consts.GET_USER_PERSONAL_SITE)
    Observable<SiteBean> getUserPersonalSite(@Path("username") String username);

    /**
     * 获取合集列表
     *
     * @param username 用户名
     * @param page     页码
     * @param perPage  页长
     * @return 合集列表
     */
    @Headers({
            Consts.HEADER_VERSION,
            Consts.HEADER_BEARER_AUTHORIZATION
    })
    @GET(Consts.GET_USER_COLLECTIONS)
    Observable<List<CollectionBean>> getUserCollections(@Path("username") String username,
                                                        @Query("page") int page,
                                                        @Query("per_page") int perPage);


    @Headers({
            Consts.HEADER_VERSION,
            Consts.HEADER_BEARER_AUTHORIZATION
    })
    @GET(Consts.GET_USER_PHOTOS)
    Observable<List<PhotoBean>> getUserPhotos(@Path("username") String username,
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
            Consts.HEADER_VERSION,
            Consts.HEADER_BEARER_AUTHORIZATION
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
            Consts.HEADER_VERSION,
            Consts.HEADER_CLIENT_ID_AUTHORIZATION
    })
    @GET(Consts.GET_CURRENT_USER_INFO)
    Observable<UserBean> getCurrentUserInfo();

    /**
     * 修改当前用户信息
     */
    @Headers({
            Consts.HEADER_VERSION,
            Consts.HEADER_CLIENT_ID_AUTHORIZATION
    })
    @FormUrlEncoded
    @PUT(Consts.UPDATE_CURRENT_USER_INFO)
    Observable<UserBean> updateCurrentUserInfo(String username);

}