package com.rainmonth.common.base.mvp;


public class BasePresenter<M extends IBaseModel, V extends IBaseView> {

    protected final String TAG = this.getClass().getSimpleName();

    protected M mModel;
    protected V mView;


    public BasePresenter(M model, V rootView) {
        this.mModel = model;
        this.mView = rootView;
    }

    public BasePresenter(V rootView) {
        this.mView = rootView;
    }

    public BasePresenter() {
    }
}
