package com.rainmonth.image.api;

import com.rainmonth.image.mvp.model.bean.CollectionBean;
import com.rainmonth.image.mvp.model.bean.PhotoBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UCollectionApi {


    /**
     * 获取合集
     *
     * @param page    页码
     * @param perPage 页长
     * @return 合集列表
     */
    @Headers({
            Consts.HEADER_VERSION,
            Consts.HEADER_BEARER_AUTHORIZATION
    })
    @GET(Consts.GET_COLLECTIONS)
    Observable<List<CollectionBean>> getCollections(@Query("page") int page,
                                                    @Query("per_page") int perPage);

    /**
     * 获取精选合集
     *
     * @param page    页码
     * @param perPage 页长
     * @return 精选合集列表
     */
    @Headers({
            Consts.HEADER_VERSION,
            Consts.HEADER_BEARER_AUTHORIZATION
    })
    @GET(Consts.GET_FEATURED_COLLECTIONS)
    Observable<List<CollectionBean>> getFeaturedCollections(@Query("page") int page,
                                                            @Query("per_page") int perPage);


    /**
     * 获取策划合集
     *
     * @param page    页码
     * @param perPage 页长
     * @return 策划合集列表
     */
    @Headers({
            Consts.HEADER_VERSION,
            Consts.HEADER_BEARER_AUTHORIZATION
    })
    @GET(Consts.GET_CURATED_COLLECTIONS)
    Observable<List<CollectionBean>> getCuratedCollections(@Query("page") int page,
                                                           @Query("per_page") int perPage);

    /**
     * 获取某个策划合集的图片
     *
     * @param id      策划合集id
     * @param page
     * @param perPage
     * @return
     */
    @Headers({
            Consts.HEADER_VERSION,
            Consts.HEADER_BEARER_AUTHORIZATION
    })
    @GET(Consts.GET_CURATED_COLLECTION_PHOTOS)
    Observable<List<PhotoBean>> getCuratedCollectionPhotos(@Path("id") long id,
                                                           @Query("page") int page,
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
            Consts.HEADER_VERSION,
            Consts.HEADER_BEARER_AUTHORIZATION
    })
    @GET(Consts.GET_COLLECTION_PHOTOS)
    Observable<List<PhotoBean>> getCollectionPhotos(@Path("id") long id,
                                                    @Query("page") int page,
                                                    @Query("per_page") int perPage);

    @Headers({
            Consts.HEADER_VERSION,
            Consts.HEADER_BEARER_AUTHORIZATION
    })
    @GET(Consts.GET_COLLECTION_DETAIL_INFO)
    Observable<CollectionBean> getCollectionDetailInfo(@Path("id") int id);
    // 采用这个的时候，单元测试获取不到结果
//    Flowable<CollectionBean> getCollectionDetailInfo(@Path("id") int id);

    @Headers({
            Consts.HEADER_VERSION,
            Consts.HEADER_BEARER_AUTHORIZATION
    })
    @FormUrlEncoded
    @POST(Consts.ADD_COLLECTION)
    Observable<Object> addCollection(@Field("title") String title,
                                     @Field("description") String description,
                                     @Field("private") boolean isPrivate);

    @FormUrlEncoded
    @POST(Consts.ADD_COLLECTION)
    Observable<Object> addCollection(@FieldMap Map<String, Object> fieldMap);

    @Headers({
            Consts.HEADER_VERSION,
            Consts.HEADER_BEARER_AUTHORIZATION
    })
    @FormUrlEncoded
    @POST(Consts.ADD_PHOTO_TO_COLLECTION)
    Observable<Object> addPhotoToCollection(@Path("collection_id") long collectionId);

    @Headers({
            Consts.HEADER_VERSION,
            Consts.HEADER_BEARER_AUTHORIZATION
    })
    @DELETE(Consts.DELETE_COLLECTION)
    Observable<Object> deleteCollection(@Path("id") long id);

    @Headers({
            Consts.HEADER_VERSION,
            Consts.HEADER_BEARER_AUTHORIZATION
    })
    @FormUrlEncoded
    @DELETE(Consts.DELETE_PHOTO_FROM_COLLECTION)
    Observable<Observable> deletePhotoFromCollection(@Path("collection_id") long collectionId);

    @Headers({
            Consts.HEADER_VERSION,
            Consts.HEADER_BEARER_AUTHORIZATION
    })
    @FormUrlEncoded
    @POST(Consts.UPDATE_COLLECTION)
    Observable<Object> updateCollection(@Path("id") long id,
                                        @Field("title") String title,
                                        @Field("description") String description,
                                        @Field("private") boolean isPrivate);

    @Headers({
            Consts.HEADER_VERSION,
            Consts.HEADER_BEARER_AUTHORIZATION
    })
    @FormUrlEncoded
    @POST(Consts.UPDATE_COLLECTION)
    Observable<Object> updateCollection(@Path("id") long id, @FieldMap Map<String, Object> fieldMap);


    @Headers({
            Consts.HEADER_VERSION,
            Consts.HEADER_BEARER_AUTHORIZATION
    })
    @GET(Consts.GET_RELATED_COLLECTIONS)
    Observable<List<CollectionBean>> getRelatedCollections(@Path("id") long id);
}
