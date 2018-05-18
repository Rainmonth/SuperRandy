package com.rainmonth.base.mvp;

import android.support.annotation.CallSuper;

import com.rainmonth.common.utils.NetworkUtils;
import com.rainmonth.support.tinker.util.SuperRandyApplicationContext;
import com.rainmonth.base.http.RequestCallback;
import com.socks.library.KLog;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.net.UnknownHostException;

import retrofit2.adapter.rxjava2.HttpException;

/**
 * Created by Administrator on 2016/8/24 0024.
 * 把回调各个方法统一处理，并且这里对返回错误做了统一处理
 */
public class BaseSubscriber<T> implements Subscriber<T> {

    private RequestCallback<T> mRequestCallback;

    public BaseSubscriber(RequestCallback<T> requestCallback) {
        mRequestCallback = requestCallback;
    }

    @CallSuper
    @Override
    public void onError(Throwable e) {
        if (mRequestCallback != null) {
            String errorMsg = null;
            if (e instanceof HttpException) {
                switch (((HttpException) e).code()) {
                    case 403:
                        errorMsg = "没有权限访问此链接！";
                        break;
                    case 504:
                        if (!NetworkUtils.isConnected(SuperRandyApplicationContext.context)) {
                            errorMsg = "没有联网哦！";
                        } else {
                            errorMsg = "网络连接超时！";
                        }
                        break;
                    default:
                        errorMsg = ((HttpException) e).message();
                        break;
                }
            } else if (e instanceof UnknownHostException) {
                errorMsg = "不知名主机";
            }
            KLog.e(e.toString());
            mRequestCallback.requestError(errorMsg);
        }
    }

    @Override
    public void onComplete() {
        if (mRequestCallback != null) {
            mRequestCallback.requestComplete();
        }
    }

    @Override
    public void onSubscribe(Subscription s) {
        if (mRequestCallback != null) {
            mRequestCallback.beforeRequest();
        }
    }

    @Override
    public void onNext(T t) {
        if (mRequestCallback != null) {
            mRequestCallback.requestSuccess(t);
        }
    }
}
