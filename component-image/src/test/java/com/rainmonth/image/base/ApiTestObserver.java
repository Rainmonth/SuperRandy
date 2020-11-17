package com.rainmonth.image.base;

import com.rainmonth.common.utils.log.LogUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class ApiTestObserver<T> implements Observer<T> {
    @Override
    public void onSubscribe(Disposable d) {
        LogUtils.d("Randy", "onSubscribe");
    }

    @Override
    public void onError(Throwable e) {
        LogUtils.d("Randy", "error happen->" + e.getMessage());
    }

    @Override
    public void onComplete() {
        LogUtils.d("Randy", "onComplete");
    }
}
