package com.rainmonth.image.api;

import com.rainmonth.common.http.BaseResponse;
import com.rainmonth.image.mvp.model.bean.CollectionBean;
import com.rainmonth.image.mvp.model.bean.PhotoBean;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UCollectionApi {


    /**
     * 获取精选合集
     *
     * @param page    页码
     * @param perPage 页长
     * @return 精选合集列表
     */
    @Headers({
            "Accept-Version:v1",
            "Authorization:Client-ID ae1715b58d53e958f990d42c9a3e221120a292efd592d66d0ba3717ccc4c9abe"
    })
    @GET(Consts.BASE_URL + "collections/featured")
    Observable<List<CollectionBean>> getFeaturedCollection(@Query("page") int page,
                                                           @Query("per_page") int perPage);


    /**
     * 获取某个合集的图片
     *
     * @param id      合集id
     * @param page    页码
     * @param perPage 页长
     * @return 图片列表
     */
    @Headers({
            "Accept-Version:v1",
            "Authorization:Client-ID ae1715b58d53e958f990d42c9a3e221120a292efd592d66d0ba3717ccc4c9abe"
    })
    @GET(Consts.BASE_URL + "collections/{id}/photos")
    Observable<List<PhotoBean>> getCollectionPhotos(@Path("id") int id,
                                                    @Query("page") int page,
                                                    @Query("per_page") int perPage);

    @Headers({
            "Accept-Version:v1",
            "Authorization:Client-ID ae1715b58d53e958f990d42c9a3e221120a292efd592d66d0ba3717ccc4c9abe"
    })
    @GET(Consts.BASE_URL + "collections/{id}")
    Observable<CollectionBean> getCollectionDetailInfo(@Path("id") int id);
    // 采用这个的时候，单元测试获取不到结果
//    Flowable<CollectionBean> getCollectionDetailInfo(@Path("id") int id);
}
