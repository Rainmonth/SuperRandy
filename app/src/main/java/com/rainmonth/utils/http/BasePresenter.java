package com.rainmonth.utils.http;

/**
 * Created by RandyZhang on 16/9/20.
 * <p>用于对请求过程中产生的Subscription 进行处理</p>
 */
public interface BasePresenter {
    void onResume();

    void onDestroy();

    void initialize();
}
