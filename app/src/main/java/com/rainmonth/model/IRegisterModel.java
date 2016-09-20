package com.rainmonth.model;

import com.rainmonth.bean.UserInfo;
import com.rainmonth.utils.http.RequestCallback;

import rx.Subscription;

/**
 * Created by RandyZhang on 16/9/19.
 * @param <T> Response object
 */
public interface IRegisterModel<T> {

    Subscription register(RequestCallback<T> callback, UserInfo userInfo);
}
