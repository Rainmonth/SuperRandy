package com.rainmonth.api;

import com.rainmonth.common.http.PageResult;
import com.rainmonth.mvp.model.bean.MemAlbumBean;

import io.reactivex.Flowable;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 记忆集Api
 * Created by RandyZhang on 2018/6/21.
 */
public interface AlbumService {
    @FormUrlEncoded
    @POST("/api/memoryAlbumCon/getMemoryAlbumList")
    Flowable<Response<PageResult<MemAlbumBean>>> getMemoryAlbumList(
            @Field("category") String category,
            @Field("page") int page,
            @Field("page_size") int pageSize);
}
