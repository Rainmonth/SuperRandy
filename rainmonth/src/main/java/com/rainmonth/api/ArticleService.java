package com.rainmonth.api;

import com.rainmonth.common.http.PageResult;
import com.rainmonth.mvp.model.bean.ArticleBean;

import io.reactivex.Flowable;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by RandyZhang on 2018/6/14.
 */

public interface ArticleService {
    @FormUrlEncoded
    @POST("api/article/getArticleList")
    Flowable<Response<PageResult<ArticleBean>>> getArticleList(@Field("page") int page,
                                                               @Field("page_size") int pageSize);
}
