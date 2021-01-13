package com.rainmonth.player;

import com.rainmonth.common.base.BaseApplication;
import com.rainmonth.player.utils.Debugger;
import com.rainmonth.utils.ResUtil;

/**
 * @author RandyZhang
 * @date 2020/11/26 3:05 PM
 */
public class PlayerApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Debugger.enable();
        }
        ResUtil.init(this);
    }

    @Override
    public void initModuleService() {

    }
}
