package com.rainmonth.view;

import com.rainmonth.base.mvp.IBaseView;
import com.rainmonth.bean.SplashBean;

/**
 * Created by RandyZhang on 16/7/1.
 */
public interface SplashView extends IBaseView {

    void initWithSplashInfo(SplashBean splashBean);

    /**
     *
     */
    void navigateTo(String navStr);
}
