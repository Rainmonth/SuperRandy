package com.rainmonth.view;

import com.rainmonth.bean.SplashInfo;
import com.rainmonth.utils.http.BaseView;

/**
 * Created by RandyZhang on 16/7/1.
 */
public interface SplashView extends BaseView{

    void initWithSplashInfo(SplashInfo splashInfo);

    /**
     *
     */
    void navigateTo(String navStr);
}
