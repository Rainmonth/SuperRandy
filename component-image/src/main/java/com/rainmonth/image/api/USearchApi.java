package com.rainmonth.image.api;

import com.rainmonth.image.mvp.model.bean.CollectionBean;
import com.rainmonth.image.mvp.model.bean.PhotoBean;
import com.rainmonth.image.mvp.model.bean.SearchBean;
import com.rainmonth.image.mvp.model.bean.SearchResult;
import com.rainmonth.image.mvp.model.bean.UserBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * @desprition: Unsplash 搜索API
 * @author: RandyZhang
 * @date: 2018/8/12 下午8:50
 */
public interface USearchApi {

    @Headers({
            Consts.HEADER_VERSION,
            Consts.HEADER_BEARER_AUTHORIZATION
    })
    @GET(Consts.SEARCH)
    Observable<SearchBean<PhotoBean, CollectionBean, UserBean>>
    search(@Query("query") String keys,
           @Query("page") int page,
           @Query("per_page") int perPage);

    /**
     * 搜索用户
     *
     * @param keys
     * @param page
     * @param perPage
     * @return
     */
    @Headers({
            Consts.HEADER_VERSION,
            Consts.HEADER_BEARER_AUTHORIZATION
    })
    @GET(Consts.SEARCH_USER)
    Observable<SearchResult<UserBean>> searchUser(@Query("query") String keys,
                                                  @Query("page") int page,
                                                  @Query("per_page") int perPage);

    /**
     * 搜索图片
     *
     * @param keys          图片关键词
     * @param collectionIds 在合集中搜索，多个合集id用逗号分隔
     * @param orientation   图片的方向，landscape、portrait、square
     * @return
     */
    @Headers({
            Consts.HEADER_VERSION,
            Consts.HEADER_BEARER_AUTHORIZATION
    })
    @GET(Consts.SEARCH_PHOTOS)
    Observable<SearchResult<PhotoBean>> searchPhotos(@Query("query") String keys,
                                                     @Query("page") int page,
                                                     @Query("per_page") int perPage,
                                                     @Query("collections") String collectionIds,
                                                     @Query("orientation") String orientation);

    /**
     * 搜索合集
     *
     * @param keys
     * @param page
     * @param perPage
     * @return
     */
    @Headers({
            Consts.HEADER_VERSION,
            Consts.HEADER_CLIENT_ID_AUTHORIZATION
    })
    @GET(Consts.SEARCH_COLLECTIONS)
    Observable<SearchResult<CollectionBean>> searchCollections(@Query("query") String keys,
                                                               @Query("page") int page,
                                                               @Query("per_page") int perPage);
}