package com.rainmonth.api;

import com.rainmonth.common.http.PageResult;
import com.rainmonth.mvp.model.bean.PursueBean;

import io.reactivex.Flowable;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @desprition: 追API请求服务
 * @author: RandyZhang
 * @date: 2018/6/26 下午4:02
 */
public interface PursueService {

    @FormUrlEncoded
    @POST("api/pursue/getPursueList")
    Flowable<Response<PageResult<PursueBean>>> getPursueList(@Field("page") int page,
                                                             @Field("page_size") int pageSize);
}