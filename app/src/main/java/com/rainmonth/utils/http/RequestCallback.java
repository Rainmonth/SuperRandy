package com.rainmonth.utils.http;

/**
 * Created by RandyZhang on 16/9/19.
 */
public interface RequestCallback<T> {
    void beforeRequest();

    void requestError(String msg);

    void requestComplete();

    void requestSuccess(T data);
}
