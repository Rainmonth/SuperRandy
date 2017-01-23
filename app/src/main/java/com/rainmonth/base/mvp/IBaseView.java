package com.rainmonth.base.mvp;

/**
 * Created by RandyZhang on 16/9/20.
 */
public interface IBaseView {
    void toast(String msg);

    void showProgress();

    void hideProgress();
}
