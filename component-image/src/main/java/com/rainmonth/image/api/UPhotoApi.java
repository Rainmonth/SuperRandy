package com.rainmonth.image.api;

import com.rainmonth.image.mvp.model.bean.PhotoBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author: Randy Zhang
 * @description: Unsplash 图片Api
 * @created: 2018/8/14
 **/
public interface UPhotoApi {

    @Headers({
            "Accept-Version:v1",
            "Authorization:Client-ID ae1715b58d53e958f990d42c9a3e221120a292efd592d66d0ba3717ccc4c9abe"
    })
    @GET(Consts.GET_PHOTOS)
    Observable<List<PhotoBean>> getPhotos(@Query("page") int page,
                                                 @Query("per_page") int per_page,
                                                 @Query("order_by") String orderBy);
    /**
     * 获取策划图片
     *
     * @param page
     * @param per_page
     * @param orderBy
     * @return
     */
    @Headers({
            "Accept-Version:v1",
            "Authorization:Client-ID ae1715b58d53e958f990d42c9a3e221120a292efd592d66d0ba3717ccc4c9abe"
    })
    @GET(Consts.GET_CURATED_PHOTOS)
    Observable<List<PhotoBean>> getCuratedPhotos(@Query("page") int page,
                                                 @Query("per_page") int per_page,
                                                 @Query("order_by") String orderBy);

    @GET(Consts.GET_PHOTO_STATISTICS)
    Observable<Object> getPhotoStatistics(@Path("id") long id,
                                          @Query("resolution") String resolutions,
                                          @Query("quantity") String quantity);

}
