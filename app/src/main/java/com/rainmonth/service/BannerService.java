package com.rainmonth.service;

import com.rainmonth.bean.ArticleBean;
import com.rainmonth.bean.BannerBean;

import java.util.List;

import retrofit2.Response;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by RandyZhang on 16/9/19.
 */
public interface BannerService {

    @GET("Banner/getHomeBanner")
    Observable<Response<List<BannerBean>>> getHomeBannerList();

    @GET("Content/getContentList")
    Observable<Response<List<ArticleBean>>> getContentList();
}
