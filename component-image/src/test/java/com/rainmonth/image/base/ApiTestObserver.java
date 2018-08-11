package com.rainmonth.image.base;

import com.socks.library.KLog;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class ApiTestObserver<T> implements Observer<T> {
    @Override
    public void onSubscribe(Disposable d) {
        KLog.d("Randy", "onSubscribe");
    }

    @Override
    public void onError(Throwable e) {
        KLog.d("Randy", "error happen->" + e.getMessage());
    }

    @Override
    public void onComplete() {
        KLog.d("Randy", "onComplete");
    }
}
