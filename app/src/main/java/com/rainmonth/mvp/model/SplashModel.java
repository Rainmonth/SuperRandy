package com.rainmonth.mvp.model;

import com.rainmonth.R;
import com.rainmonth.common.base.mvp.BaseModel;
import com.rainmonth.common.utils.DateUtils;
import com.rainmonth.mvp.contract.SplashContract;
import com.rainmonth.mvp.model.bean.SplashBean;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * 获取启动页数据
 * Created by RandyZhang on 2018/5/30.
 */

public class SplashModel extends BaseModel implements SplashContract.Model {
    @Inject
    public SplashModel() {
    }


//    @Override
//    public Flowable<SplashBean> getSplashInfo() {
//        return null;
//    }


    @Override
    public SplashBean getSplashInfo() {
        SplashBean splashBean = new SplashBean();
        splashBean.setHasRemoteSplash(true);
        String hour = DateUtils.getHour();
        int intHour = Integer.parseInt(hour);
        if (intHour >= 0 && intHour < 4) {
            splashBean.setRemoteSplashText("荏苒追寻");
            splashBean.setRemoteSplashUrl(R.drawable.default_splash);
        }
        if (intHour >= 4 && intHour < 8) {
            splashBean.setRemoteSplashText("天道酬勤");
            splashBean.setRemoteSplashUrl(R.drawable.bg1);
        }
        if (intHour >= 8 && intHour < 12) {
            splashBean.setRemoteSplashText("树的方向由风决定，人生的方向由自己决定！");
            splashBean.setRemoteSplashUrl(R.drawable.bg2);
        }
        if (intHour >= 12 && intHour < 16) {
            splashBean.setRemoteSplashText("精益求精");
            splashBean.setRemoteSplashUrl(R.drawable.bg3);
        }
        if (intHour >= 16 && intHour < 20) {
            splashBean.setRemoteSplashText("Enjoy Yourself!");
            splashBean.setRemoteSplashUrl(R.drawable.bg4);
        }
        if (intHour >= 20) {
            splashBean.setRemoteSplashText("Good Night!");
            splashBean.setRemoteSplashUrl(R.drawable.bg5);
        }

        splashBean.setNaveTo("main");
        return splashBean;
    }
}
