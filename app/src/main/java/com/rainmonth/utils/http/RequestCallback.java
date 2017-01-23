package com.rainmonth.utils.http;

/**
 * Created by RandyZhang on 16/9/19.
 *
 * @param <T> 请求返回的对象
 */
public interface RequestCallback<T> {
    void beforeRequest();

    void requestError(String msg);

    void requestComplete();

    void requestSuccess(T data);
}
