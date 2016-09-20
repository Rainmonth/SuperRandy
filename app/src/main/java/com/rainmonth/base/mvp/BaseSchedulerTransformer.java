package com.rainmonth.base.mvp;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by RandyZhang on 16/9/20.
 * 定义默认线程模型，一般都是在io线程发起request，在主线程中处理response，在io线程中unsubscribe
 */
public class BaseSchedulerTransformer<T> implements Observable.Transformer<T, T> {
    @Override
    public Observable<T> call(Observable<T> tObservable) {
        return tObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).unsubscribeOn(Schedulers.io());
    }
}
