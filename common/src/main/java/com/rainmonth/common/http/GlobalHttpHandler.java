package com.rainmonth.common.http;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 全局处理Http请求和相应的接口
 * Created by RandyZhang on 2018/6/1.
 */

public interface GlobalHttpHandler {
    Request onHttpRequestBefore(Interceptor.Chain chain, Request request);

    Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response);

    /**
     * 空实现
     */
    GlobalHttpHandler EMPTY = new GlobalHttpHandler() {
        @Override
        public Request onHttpRequestBefore(Interceptor.Chain chain, Request request) {
            return request;
        }

        @Override
        public Response onHttpResultResponse(String httpResult,
                                             Interceptor.Chain chain,
                                             Response response) {
            return response;
        }
    };
}
