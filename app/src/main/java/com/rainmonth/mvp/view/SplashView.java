package com.rainmonth.mvp.view;

import com.rainmonth.common.base.mvp.IBaseView;
import com.rainmonth.mvp.model.bean.SplashBean;

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
