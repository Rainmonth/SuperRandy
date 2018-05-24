package com.rainmonth.mvp.contract;

import com.rainmonth.common.base.mvp.IBaseModel;
import com.rainmonth.common.base.mvp.IBaseView;
import com.rainmonth.mvp.model.bean.SplashBean;

/**
 * Created by RandyZhang on 2018/5/21.
 */

public interface SplashContract {
    interface View extends IBaseView {
        void initWithSplashInfo(SplashBean splashBean);

        /**
         *
         */
        void navigateTo(String navStr);
    }

    interface Model extends IBaseModel {

    }

}
