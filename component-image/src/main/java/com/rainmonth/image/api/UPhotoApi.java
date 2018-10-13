package com.rainmonth.image.api;

import com.rainmonth.image.mvp.model.bean.PhotoBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.rainmonth.image.api.Consts.GET_DAILY_PHOTO;

/**
 * @author: Randy Zhang
 * @description: Unsplash 图片Api
 * @created: 2018/8/14
 **/
public interface UPhotoApi {

    @Headers({
            Consts.HEADER_VERSION,
            Consts.HEADER_BEARER_AUTHORIZATION
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
            Consts.HEADER_VERSION,
            Consts.HEADER_BEARER_AUTHORIZATION
    })
    @GET(Consts.GET_CURATED_PHOTOS)
    Observable<List<PhotoBean>> getCuratedPhotos(@Query("page") int page,
                                                 @Query("per_page") int per_page,
                                                 @Query("order_by") String orderBy);

    @GET(Consts.GET_PHOTO_STATISTICS)
    Observable<Object> getPhotoStatistics(@Path("id") long id,
                                          @Query("resolution") String resolutions,
                                          @Query("quantity") String quantity);


    // 获取每日图片
//    @GET(Consts.GET_DAILY_PHOTO)
//    @GET("https://unsplash.com/napi/feeds/home")
    @Headers({
            "user-agent:Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36"
    })
    @GET(Consts.GET_DAILY_PHOTO)
    Observable<PhotoBean> getDailyPhoto();
}
