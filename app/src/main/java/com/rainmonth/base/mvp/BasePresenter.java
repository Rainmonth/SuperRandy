package com.rainmonth.base.mvp;

import com.rainmonth.utils.http.RequestCallback;
import com.socks.library.KLog;

import rx.Subscription;

/**
 * Created by RandyZhang on 16/9/20.
 *
 * @param <T> view class inherit from BaseView
 * @param <V> response data from request
 */
public class BasePresenter<T extends BaseView, V> implements IBasePresenter, RequestCallback<V> {
    protected Subscription mSubscription;
    protected T mView;

    public BasePresenter(T mView) {
        this.mView = mView;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
        mView = null;
    }

    @Override
    public void beforeRequest() {
        mView.showProgress();
    }

    @Override
    public void requestError(String msg) {
        KLog.e(msg);
        mView.toast(msg);
        mView.hideProgress();
    }

    @Override
    public void requestComplete() {
        mView.hideProgress();
    }

    @Override
    public void requestSuccess(V data) {

    }
}
