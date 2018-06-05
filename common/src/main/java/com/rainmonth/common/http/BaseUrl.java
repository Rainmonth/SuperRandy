package com.rainmonth.common.http;

import okhttp3.HttpUrl;

/**
 * 动态确定请求的baseUrl
 * Created by RandyZhang on 2018/6/1.
 */

public interface BaseUrl {
    /**
     * 使用Retrofit 调用Api之前，先确定baseUrl并通过该方法返回
     *
     * @return baseUrl
     */
    HttpUrl url();
}
