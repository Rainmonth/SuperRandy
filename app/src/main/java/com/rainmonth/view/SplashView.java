package com.rainmonth.view;

import com.rainmonth.bean.SplashBean;
import com.rainmonth.base.mvp.BaseView;

/**
 * Created by RandyZhang on 16/7/1.
 */
public interface SplashView extends BaseView{

    void initWithSplashInfo(SplashBean splashBean);

    /**
     *
     */
    void navigateTo(String navStr);
}
