package com.rainmonth.api;

import com.rainmonth.mvp.model.bean.ArticleBean;
import com.rainmonth.mvp.model.bean.BannerBean;
import com.rainmonth.common.http.Result;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by RandyZhang on 16/9/19.
 */
public interface BannerService {

    @FormUrlEncoded
    @POST("api/banner/getBannerList")
    Flowable<Response<Result<List<BannerBean>>>> getHomeBannerList(@Field("page") int page,
                                                                   @Field("page_size") int pageSize,
                                                                   @Field("type") int type);

    @GET("Content/getContentList")
    Flowable<Response<List<ArticleBean>>> getContentList();
}
