package com.rainmonth.common.http;

/**
 * 请求结果通用定义
 * Created by RandyZhang on 2018/6/11.
 */

public class Result<T> extends BaseResponse {
    T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
