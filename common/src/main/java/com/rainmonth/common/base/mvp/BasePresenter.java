package com.rainmonth.common.base.mvp;


import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BasePresenter<M extends IBaseModel, V extends IBaseView> implements IBasePresenter {

    protected final String TAG = this.getClass().getSimpleName();
    protected CompositeDisposable mCompositeDisposable;

    protected M mModel;
    protected V mView;


    public BasePresenter(M model, V rootView) {
        this.mModel = model;
        this.mView = rootView;
    }

    public BasePresenter(V rootView) {
        this.mView = rootView;
        onStart();
    }

    public BasePresenter() {
        onStart();
    }

    @Override
    public void onStart() {
        // 做一些事件注册相关工作
    }

    @Override
    public void onDestroy() {
        unSubscribe();
        if (mModel != null) {
            mModel.onDestroy();
            this.mModel = null;
        }
        this.mView = null;
    }

    protected void addSubscribe(Disposable subscription) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(subscription);
    }

    protected void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }
}
