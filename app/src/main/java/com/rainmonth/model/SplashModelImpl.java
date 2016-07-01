package com.rainmonth.model;

import com.rainmonth.R;
import com.rainmonth.bean.SplashInfo;
import com.rainmonth.utils.DateUtils;

/**
 * Created by RandyZhang on 16/7/1.
 */
public class SplashModelImpl implements SplashModel{

    @Override
    public SplashInfo getSplashInfo() {
        SplashInfo splashInfo = new SplashInfo();
        splashInfo.setHasRemoteSplash(true);
        String hour = DateUtils.getHour();
        int intHour = Integer.parseInt(hour);
        if (intHour >= 0 && intHour < 4) {
            splashInfo.setRemoteSplashText("荏苒追寻");
            splashInfo.setRemoteSplashUrl(R.drawable.default_splash);
        }
        if (intHour >= 4 && intHour < 8) {
            splashInfo.setRemoteSplashText("天道酬勤");
            splashInfo.setRemoteSplashUrl(R.drawable.bg1);
        }
        if (intHour >= 8 && intHour < 12) {
            splashInfo.setRemoteSplashText("树的方向由风决定，人生的方向由自己决定！");
            splashInfo.setRemoteSplashUrl(R.drawable.bg2);
        }
        if (intHour >= 12 && intHour < 16) {
            splashInfo.setRemoteSplashText("精益求精");
            splashInfo.setRemoteSplashUrl(R.drawable.bg3);
        }
        if (intHour >= 16 && intHour < 20) {
            splashInfo.setRemoteSplashText("Enjoy Yourself!");
            splashInfo.setRemoteSplashUrl(R.drawable.bg4);
        }
        if (intHour >= 20) {
            splashInfo.setRemoteSplashText("Good Night!");
            splashInfo.setRemoteSplashUrl(R.drawable.bg5);
        }

        splashInfo.setNaveTo("main");
        return  splashInfo;
    }
}
