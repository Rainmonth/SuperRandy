package com.rainmonth.api;

import com.rainmonth.common.http.BaseResponse;
import com.rainmonth.common.http.Result;
import com.rainmonth.mvp.model.bean.XunNavigationBean;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.Response;
import retrofit2.http.GET;

/**
 * @desprition: UI动态展示配置的Service
 * @author: RandyZhang
 * @date: 2018/6/22 下午2:05
 */
public interface NavService {

    @GET("/api/navCon/getXunNavList")
    Flowable<Response<Result<List<XunNavigationBean>>>> getXunNavList();

    @GET("/api/navCon/getMainNavList")
    Flowable<Response<BaseResponse>> getMainNavList();
}