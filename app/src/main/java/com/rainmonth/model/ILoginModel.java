package com.rainmonth.model;

import com.rainmonth.utils.http.RequestCallback;

import rx.Subscription;

/**
 * Created by RandyZhang on 16/9/19.
 */
public interface ILoginModel<T> {

    Subscription login(RequestCallback<T> callback, String username, String psw);
}
