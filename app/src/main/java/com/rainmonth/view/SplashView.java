package com.rainmonth.view;

import com.rainmonth.bean.SplashInfo;

/**
 * Created by RandyZhang on 16/7/1.
 */
public interface SplashView {

    void initWithSplashInfo(SplashInfo splashInfo);

    /**
     *
     */
    void navigateTo(String navStr);
}
