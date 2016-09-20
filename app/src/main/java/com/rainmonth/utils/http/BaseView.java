package com.rainmonth.utils.http;

/**
 * Created by RandyZhang on 16/9/20.
 */
public interface BaseView {

    void initialize();

    void toast(String msg);

    void showProgress();

    void hideProgress();
}
